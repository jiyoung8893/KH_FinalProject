package com.dht.www.shopping.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
}
