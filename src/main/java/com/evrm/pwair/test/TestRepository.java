package com.evrm.pwair.test;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestRepository {

    @Select("SELECT * FROM TEST")
    public List<Test> findAll();

    @Select("SELECT * FROM TEST WHERE ID = #{id}")
    public Test findById(Long id);

    @Insert("INSERT INTO TEST VALUES(#{id}, #{name})")
    public int addTest(Test test);

    @Delete("DELETE FROM TEST WHERE ID = #{id}")
    public int removeTest(Long id);
}
