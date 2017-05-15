package com.bs.dao.mapping;

import com.bs.dao.entity.Suggestion;
import com.bs.dao.entity.SuggestionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuggestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int countByExample(SuggestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int deleteByExample(SuggestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int insert(Suggestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int insertSelective(Suggestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    List<Suggestion> selectByExample(SuggestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    Suggestion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Suggestion record, @Param("example") SuggestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Suggestion record, @Param("example") SuggestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Suggestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table suggestion
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Suggestion record);
}