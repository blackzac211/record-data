package unist.record.graph.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import unist.record.common.CommonSecurity;
import unist.record.common.DBManager;
import unist.record.common.ExcelManager;

@Controller
public class GraphController {
	
	public static void main(String[] args) {
		try {
			ExcelManager excel = new ExcelManager();
		    excel.setRow(0);
			excel.setCell(0);
			excel.setCellValue("Date");
			excel.setCellValue("Max Count");
			
			DBManager db = new DBManager();
    		String sql;
    		String sql_detail;
    		
    		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    		// Calendar cal = Calendar.getInstance();
    		// System.out.println("start: " + df.format(cal.getTime()));
    		
    		PreparedStatement pstmt;
    		ResultSet rs;
    		ResultSet rs_detail;
    		
    		Calendar startDate = Calendar.getInstance();
    		startDate.set(2020, 0, 1, 0, 0, 0);
    		Calendar endDate = Calendar.getInstance();
    		endDate.set(2020, 3, 20, 23, 59, 59);
    		String startTime;
    		String endTime;
    		String curTime;
    		HashMap<String, Integer> map = new HashMap<String, Integer>();
    		
    		// for(int i = startDate; i <= endDate; i++) {
    		String curDate;
    		int detailCnt;
    		int resCnt;
    		int i;
    		while(true) {
    			if(startDate.getTimeInMillis() > endDate.getTimeInMillis()) {
    				break;
    			}
    			curDate = df.format(startDate.getTime());
    			System.out.println("curDate: " + curDate);
    			
	    		sql = "SELECT user_id, user_name, rec_startdate, rec_starttime, rec_enddate, rec_endtime " + 
	    				"FROM rec_data " + 
	    				"WHERE rec_startDate = ? " + 
	    				"ORDER BY rec_starttime ";
	    		pstmt = db.getPreparedStatement(sql);
	    		pstmt.setString(1, curDate);
	    		rs = pstmt.executeQuery();
	    		
	    		map.put(curDate, 0);
	    		while(rs.next()) {
	    			startTime = rs.getString("rec_starttime");
	    			endTime = rs.getString("rec_endtime");
	    			
	    			sql_detail = "SELECT count(*) " + 
	        				"FROM rec_data " + 
	        				"WHERE rec_startDate = ? AND ((rec_starttime >= ? and rec_starttime <= ?) OR (rec_endtime >= ? AND rec_endtime <= ?))"; 
	    			
	    			pstmt = db.getPreparedStatement(sql_detail);
	    			pstmt.setString(1, curDate);
	    			pstmt.setString(2, startTime);
	        		pstmt.setString(3, endTime);
	    			pstmt.setString(4, startTime);
	        		pstmt.setString(5, endTime);
	        		rs_detail = pstmt.executeQuery();
	        		rs_detail.next();
	        		detailCnt = rs_detail.getInt(1);
	        		
	        		i = 0;
	        		if(map.get(curDate) < detailCnt) {
	        			while(true) {
		        			curTime = String.valueOf(Integer.valueOf(startTime) + (i++ * 60));
		        			if(Integer.valueOf(endTime) < Integer.valueOf(curTime)) {
		        				break;
		        			}
		        			
		        			sql_detail = "SELECT count(*)" + 
		        					"FROM rec_data " + 
		        					"WHERE rec_startDate=? AND rec_starttime <= ? AND rec_endtime >= ?";
		        			pstmt = db.getPreparedStatement(sql_detail);
			    			pstmt.setString(1, curDate);
			    			pstmt.setString(2, curTime);
			        		pstmt.setString(3, curTime);
			        		rs_detail = pstmt.executeQuery();
			        		rs_detail.next();
			        		resCnt = rs_detail.getInt(1);
			        		
			        		if(map.get(curDate) < resCnt) {
			        			map.put(curDate, resCnt);
			        		}
	        			}
	        		}
	    		}
	    		
	    		excel.nextRow();
		    	excel.setCell(0);
		    	excel.setCellValue(curDate);
		    	excel.setCellValue(String.valueOf(map.get(curDate)));

    			startDate.add(Calendar.DATE, 1);
    		}
    		
			File file = new File("E:/export_record.xls");
		    OutputStream out = new FileOutputStream(file);
		    excel.getWorkbook().write(out);
		    out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	  @RequestMapping("/graph/selectRecordLogs.do")
	    public void selectRecordLogs(String id_up, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	    	try {
	    		if(!CommonSecurity.checkReferer(request)) {
	    			throw new Exception("Exploiting cross-site scripting in Referer header.");
	    		}
	    		
	    		
	    		JSONObject json = new JSONObject();
	        	// json.put("list", list);
	        	response.setContentType("text/json;charset=utf-8");
	        	response.getWriter().print(json.toString());
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	  
}

