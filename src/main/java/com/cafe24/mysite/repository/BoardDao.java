package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class BoardDao {
	
	@Autowired
	SqlSession sqlSession;
	
	
	/*
	 * g_no(2) = 유지 => 2
	 * o_no(1) = o_no(1) + 1 => 2
	 * d(0) = d + 1 => 1
	 * update o_no = o_no+1 where g_no = 2 and o_no >= 2
	 * insert (~~~~ d(d(0) + 1) ~~~~~~)
	 */
	public boolean insertRepost(BoardVo repostBoard, BoardVo parentBoard, UserVo authUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", repostBoard.getTitle());
		map.put("content", repostBoard.getContent());
		map.put("groupNo", parentBoard.getGroupNo());
		map.put("orderNo", parentBoard.getOrderNo() + 1);
		map.put("depth", parentBoard.getDepth() + 1);
		map.put("userNo", authUser.getNo());
		map.put("deleted", repostBoard.isDeleted());
		int count = sqlSession.insert("insertRepost", map);
		
		
		return count == 1;
	}
		
	
	public boolean repostUpdate(Long pGroupNo, Long pOrderNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pGroupNo", pGroupNo);
		map.put("pOrderNo", pOrderNo);
		
		int count = sqlSession.update("board.repostUpdate", map);
		
		return count == 1;
	}
	
	
	public boolean insertNewPost(BoardVo vo) {
		int count = sqlSession.insert("insertNewPost", vo);
		
		return count == 1;
	}
		
		
	public boolean deletePost(Long no) {
		int count = sqlSession.delete("board.deletePost", no);
		
		return count == 1;
	}
	
	
	public boolean updatePost(BoardVo newVo) {
		int count = sqlSession.update("board.updatePost", newVo);
		
		return count == 1;
	}
	
		
	public BoardVo getBoard(Long no) {
		BoardVo theBoard = sqlSession.selectOne("board.selectTheBoard", no);
		
		
		updateHit(no);
		
		
		return theBoard;
	}
	
	
	public boolean updateHit(Long no) {
		int count = sqlSession.update("board.updateHit", no);
		
		return count == 1;
	}
	
	
	public List<BoardVo> select(Long recentPage, Long listCount, Integer pageCount, String keyword){
		Map<String, Object> map = new HashMap<String, Object>();
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		map.put("keyword", keyword);
		map.put("start", (recentPage - 1) * pageCount);
		map.put("listCount", listCount);
		
		
		List<BoardVo> list = sqlSession.selectList("board.selectAllBoardInPage", map);
		
		
		return list;
	}
	
	public Long getTotalCount(String keyword) {
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		Long totalCount = sqlSession.selectOne("board.getTotalCount", keyword);
		
		return totalCount;
	}


	/*
	
	
	public boolean insertRepost(BoardVo repostBoard, BoardVo parentBoard, UserVo authUser) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		repostUpdate(parentBoard.getGroupNo(), parentBoard.getOrderNo());
				
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					"insert " + 
					"into board " + 
					"values( " + 
					"	null, " + 
					"    ?, " + 			//title
					"    ?, " + 			//content 
					"    ?, " + 			//groupNo 
					"    ?, " + 			//orderNo
					"    ?, " + 			//depth	
					"    now(), " + 		//regDate
					"    0, " + 			//hit
					"    ?," +				//userNo 
					"	 ?)";				//isDeleted
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, repostBoard.getTitle());
			pstmt.setString(2, repostBoard.getContent());
			pstmt.setLong(3, parentBoard.getGroupNo());
			pstmt.setLong(4, parentBoard.getOrderNo() + 1);
			pstmt.setLong(5, parentBoard.getDepth() + 1);
			pstmt.setLong(6, authUser.getNo());
			pstmt.setBoolean(7, repostBoard.isDeleted());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public boolean respostUpdate(Long pGroupNo, Long pOrderNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = "update board set order_no = order_no + 1 where group_no = ? and order_no >= ? + 1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, pGroupNo);
			pstmt.setLong(2, pOrderNo);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public boolean insertNewPost(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"insert " + 
					"into board " + 
					"values( " + 
					"	null, " + 
					"	?, " + 		//title
					"   ?, " + 		//content 
					"   ifnull( (select MAX(group_no) from board max_board), 0) + 1, " + //groupNo 
					"   ?, " + 		//orderNo
					"   ?, " + 		//depth	
					"   now(), " + 	//regDate
					"   ?, " + 		//hit
					"   ?, " + 		//userNo
					"   ? " + 		//isdeleted
					"	) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getOrderNo());
			pstmt.setLong(4, vo.getDepth());
			pstmt.setLong(5, vo.getHit());
			pstmt.setLong(6, vo.getUserNo());	
			pstmt.setBoolean(7, vo.isDeleted());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public boolean delete(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = "update board set is_deleted = 1 where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public boolean update(BoardVo newVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"update board set title=?, content=? where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newVo.getTitle());
			pstmt.setString(2, newVo.getContent());
			pstmt.setLong(3, newVo.getNo());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
		
	
	public boolean updateHit(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql =
					"update board set hit = hit + 1 where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public BoardVo getBoard(Long no) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
						
			String sql = 
					" select " +
					" 	no, " + 
					"	title, " + 
					"	content, " + 
					"	group_no, " + 
					"	order_no, " + 
					"	depth, " + 
					"	regDate, " + 
					"	hit, " + 
					"	user_no " + 
					" from board " + 
					" where no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setGroupNo(rs.getLong(4));
				vo.setOrderNo(rs.getLong(5));
				vo.setDepth(rs.getLong(6));
				vo.setRegDate(rs.getString(7));
				vo.setHit(rs.getLong(8));
				vo.setUserNo(rs.getLong(9));
			}
			
			updateHit(no);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;		
	}
	 
	
	public List<BoardVo> select(Long recentPage, Long listCount, Integer pageCount, String keyword){
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" select " + 
					"	b.no, " + 
					"	b.title, " + 
					"	u.name, " + 
					"	u.no, " + 
					"	b.hit, " + 
					"	b.regDate, " + 
					"	b.depth, " +
					"	b.is_deleted " +
					" from board b join users u on b.user_no = u.no " +
					" where content LIKE ? " + 
					" order by group_no desc, order_no asc " + 
					" LIMIT ?, ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			pstmt.setLong(2, (recentPage - 1) * pageCount);
			pstmt.setLong(3, listCount);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String userName = rs.getString(3);
				Long userNo = rs.getLong(4);
				Long hit = rs.getLong(5);
				String regDate = rs.getString(6);
				Long depth = rs.getLong(7);
				Boolean deleted = rs.getBoolean(8);
				
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setDeleted(deleted);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Long getTotalCount(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long totalCount = 0L;
		
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = 
					" select " +
					" 	count(*) " +
					" from board " + 
					" where content LIKE ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getLong(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.close(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return totalCount;
	}
	*/

}
