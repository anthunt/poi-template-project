package com.anthunt.poi.demo.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.anthunt.poi.mapper.dao.AbstractMapperDAO;
import com.anthunt.poi.mapper.model.CellDefinition;
import com.anthunt.poi.mapper.model.ColumnConstraintDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinition;
import com.anthunt.poi.mapper.model.DAOParams;
import com.anthunt.poi.mapper.model.DBColumnDefinition;
import com.anthunt.poi.mapper.model.SheetDefinition;
import com.anthunt.poi.template.helper.PoiLogger;

public class PoiDemoDAO extends AbstractMapperDAO {

	private static PoiLogger logger = PoiLogger.getLogger(PoiDemoDAO.class);
	
	private SqlSession sqlSession;
	private static String MAPPER_NAMESPACE = "com.anthunt.poi.demo.db.PoiDemoDAO";
	
	public PoiDemoDAO() throws IOException {
		logger.trace("start creation PoiDemoDAO instance.");
		
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		logger.trace("PoiDemoDAO got mybatis-config.xml.");
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		logger.trace("PoiDemoDAO built SqlSessionFactory.");
		
		this.sqlSession = sqlSessionFactory.openSession(false);
		logger.trace("PoiDemoDAO SqlSession opened.");
		logger.debug("PoiDemoDAO instance created.");
	}
	
	private String getMapper(String mapperId) {
		logger.debug("PoiDemoDAO mapper = {}.{}", MAPPER_NAMESPACE, mapperId);
		return MAPPER_NAMESPACE + "." + mapperId;
	}

	@Override
	protected Connection applyConnection() {
		logger.trace("called applyConnection()");
		return this.sqlSession.getConnection();
	}

	@Override
	protected List<SheetDefinition> applySheetDefinitions(DAOParams daoParams) {
		logger.trace("called applySheetDefinitions({})", daoParams);
		return this.sqlSession.selectList(this.getMapper("getSheets"), daoParams.getExcelIdx());
	}

	@Override
	protected List<ColumnDefinition> applyColumnDefinitions(DAOParams daoParams) {
		logger.trace("called applyColumnDefinitions({})", daoParams);
		
		List<ColumnDefinition> columnDefinitions = this.sqlSession.selectList(this.getMapper("getColumns"), daoParams);
		for (ColumnDefinition columnDefinition : columnDefinitions) {
			String explicitSql = columnDefinition.getExplicitSql();
			if(explicitSql != null) {
				Connection con = this.sqlSession.getConnection();
				PreparedStatement pstmt = null;
				try {
					List<String> dataExplicits = new ArrayList<>();
					pstmt = con.prepareStatement(explicitSql);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						dataExplicits.add(rs.getString(1));
					}
					
					columnDefinition.setDataExplicits(dataExplicits);
					
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					if(pstmt != null) try { pstmt.close(); } catch(SQLException skip) {}
					if(con != null) try { con.commit(); } catch(SQLException skip) {}
				}
				
			}
		}
		return columnDefinitions;
	}

	@Override
	protected ColumnConstraintDefinition applyColumnConstraintDefinition(DAOParams daoParams) {
		logger.trace("called applyColumnConstraintDefinition({})", daoParams);
		return this.sqlSession.selectOne(this.getMapper("getColumnConst"), daoParams);
	}

	@Override
	protected List<CellDefinition> applyExplainCellDefinitions(DAOParams daoParams) {
		logger.trace("called applyExplainCellDefinitions({})", daoParams);
		return this.sqlSession.selectList(this.getMapper("getExplains"), daoParams);
	}

	@Override
	protected List<CellDefinition> applyHeaderCellDefinitions(DAOParams daoParams) {
		logger.trace("called applyHeaderCellDefinitions({})", daoParams);
		return this.sqlSession.selectList(this.getMapper("getHeaders"), daoParams);
	}

	@Override
	protected List<DBColumnDefinition> applyDBColumnDefinitions(DAOParams daoParams) {
		logger.trace("called applyDBColumnDefinitions({})", daoParams);
		return this.sqlSession.selectList(this.getMapper("getDBColumns"), daoParams);
	}
	
}
