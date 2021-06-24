# 常用示例



## 返回自增主键

> - [MyBatis 插入数据后返回自增主键 ID 详解](https://www.cnblogs.com/charlypage/p/11253610.html)
>   - insert 操作**返回的是记录数**并非记录主键 ID
>   - 获取新添加记录的主键值，需要在执行添加操作之后，读取  Java 对象的主键属性

XML

```xml
<insert id="insert" useGeneratedKeys="true" keyProperty="id" keyColumn="ID">
  INSERT INTO ...
</insert>
```

接口

```java
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
@Insert("INSERT INTO ...")
void insert(XxxVO vo);
```



## 批量插入

```xml
<insert id="insertBatch" useGeneratedKeys="true" keyProperty="id" keyColumn="ID">
  insert into user ( name, age ) values
  <foreach collection="users" item="user" separator=",">
    (#{user.name}, #{user.age})
  </foreach>
</insert>

<!-- -->
Integer insertBatch(@Param("users") Collection<UserVO> users);
```



## IN 查询

```xml
<select id="selectBatch" resultType="xyz.kail.demo.mybatis.spring.boot.model.mybatis.UserVO">
  select * from `user`
  where id in
  <foreach collection="userIds" item="userId" open="(" separator="," close=")">
    #{userId}
  </foreach>
</select>

<!-- -->
List<UserVO> selectBatch(@Param("userIds") Collection<Long> userIds);
```



## result* 的区别

> [mybatis – MyBatis 3 | XML 映射器 | select](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#select)

|     属性      | 描述                                                         |
| :-----------: | ------------------------------------------------------------ |
| `resultType`  | 返回结果的**类全限定名**或别名。 注意：集合应该设置为**集合包含的类型**，而**不是集合本身的类型<br /> `resultType` 和 `resultMap` 之间只能同时使用一个 |
|  `resultMap`  | 对外部  `resultMap` 的命名**引用**，**[结果映射](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Result_Maps) 是 MyBatis 最强大的特性**<br />`resultType` 和 `resultMap` 之间只能同时使用一个。 |
|               |                                                              |
| resultSetType | 原生 JDBC 的 `java.sql.ResultSet` 的配置<br />**FORWARD_ONLY**： 只能向前滚动；<br />**SCROLL_SENSITIVE**： 双向滚动，更新敏感（每次 next 进行真实的查询）<br />**SCROLL_INSENSITIVE**： 双向滚动，更新不敏感 |
|               |                                                              |
|  resultSets   | **仅适用于多结果集的情况**。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |
|               |                                                              |
| resultOrdered | **仅针对嵌套结果 select 语句**，详见：[resultOrdered="true" 的使用](https://blog.csdn.net/weixin_40240756/article/details/108889127) |



## 注解中的 动态 SQL

> - 使用 `<script>` 标签包裹

```java
@Update("<script>" +
        "update user " +
        "   <set>" +
        "       <if test='user.name != null'>name = #{user.name},</if>" +
        "   </set>" +
        "where id = #{user.id}" +
        "</script>" +
        "")
Integer updateScript(@Param("user") UserVO user);
```



## if 条件导致 SQL 问题

> [trim、where、set](https://mybatis.org/mybatis-3/zh/dynamic-sql.html#trim.E3.80.81where.E3.80.81set)

### 查询 where

- `<where>` 元素在**子元素有内容的情况下**才插入 `WHERE` 子句
- 若子句的开头为 `AND` 或 `OR`，`<where>` 元素也会将它们去除

```xml
<select id="selectWhere" resultType="xyz.kail.demo.mybatis.spring.boot.model.mybatis.UserVO">
  select * from `user`
  <where>
    <if test="user != null and user.name != null">
      AND name = #{user.name}
    </if>
    <if test="user != null and user.age != null">
      AND age = #{user.age}
    </if>
  </where>
</select>
```



### 更新 set

- `<set>` 元素会动态地在行首插入 `SET` 关键字
- 并会删掉额外的逗号

```xml
<update id="updateSet">
  update user
  <set>
    <if test="user != null and user.name != null">
      name = #{user.name},
    </if>
    <if test="user != null and user.age != null">
      age = #{user.age},
    </if>
  </set>
  <where>
    <if test="user != null and user.age != null">
      AND id = #{user.id}
    </if>
  </where>
</update>
```



### 通用 trim

- `prefix` / `suffix` 需要在前后追加的字符
- `prefixOverrides` / `suffixOverrides` 需要在前后删除的字符，多个用 `|` 管道符分割

```xml
<!-- 替代 where 标签 -->
<!-- 替代 where 标签 -->
<!-- 替代 where 标签 -->
<select id="selectTrim" resultType="xyz.kail.demo.mybatis.spring.boot.model.mybatis.UserVO">
  select * from `user`
  <trim prefix="where" prefixOverrides="AND | OR">
    <if test="user != null and user.name != null">
      AND name = #{user.name}
    </if>
    <if test="user != null and user.age != null">
      AND age = #{user.age}
    </if>
  </trim>
</select>

<!-- 替代 set 标签 -->
<!-- 替代 set 标签 -->
<!-- 替代 set 标签 -->
<update id="updateTrim">
  update user
  <trim prefix="set" suffixOverrides="," >
    <if test="user != null and user.name != null">
      name = #{user.name},
    </if>
    <if test="user != null and user.age != null">
      age = #{user.age},
    </if>
  </trim>
  <where>
    <if test="user != null and user.age != null">
      AND id = #{user.id}
    </if>
  </where>
</update>
```



## 执行原始 SQL

### 错误的方式

> 以下方式不行的原因是： **参数不能传原始 SQL，而是 Mapper 的全限定名**

```java
try (final SqlSession sqlSession = sqlSessionFactory.openSession()){
  //
  // ××× 参数不能传原始 SQL
  sqlSession.update("select count(*) from user"); 
  //
  // √√√ 是 Mapper 的全限定名
  sqlSession.select(UserMapper.class.getName() + ".count", defaultResultHandler); 
}
```



### 方式1 ${sql} 

```java
@Mapper
public interface SqlMapper {

  /**
   * 参数传入完整的 SQL
   */
  @Update("${sql}")
  void update(@Param("sql") String sql);

}
```



### 方式2 @XxxProvider

> [@SqlProvider 注解](https://mybatis.org/mybatis-3/zh/java-api.html#a.E6.98.A0.E5.B0.84.E6.B3.A8.E8.A7.A3.E7.A4.BA.E4.BE.8B)

```java
```

