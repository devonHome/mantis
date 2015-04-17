package com.smt.mantis.procedure.ticket;

//JDK 1.7.0
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//Mantis Rep. 2.0
import com.smt.mantis.procedure.ProcedureBase;
//javax 1.7.x

/****************************************************************************
 * <b>Title</b>: TicketProcedure.java <p/>
 * <b>Project</b>: MantisReport <p/>
 * <b>Copyright:</b> Copyright (c) 2014<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author Devon Franklin
 * @version 2.0
 * @since March 5, 2015
 ****************************************************************************/

public class TicketProcedure extends ProcedureBase {

	private TicketBuilder ticketBuild = null;

	/**	
	 * Constructor to initialize class
	 * @param ds
	 */
	public TicketProcedure() {
		super();
	}

	/**
	 * Builds a list of tickets based on sql query
	 * @param requestMap
	 * @return list of ticketVOs
	 */
	public ArrayList<TicketVO> selectQuery(HashMap<String, String[]> requestMap) {

		ArrayList<TicketVO> ticketList = new ArrayList<TicketVO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.getConnection();

			// build SQL query
			ticketBuild = new TicketBuilder(requestMap);
			String ticketQuery = ticketBuild.buildQuery();
			log.debug(ticketBuild.getSqlColumnNames().toString());
			
			ps = dbConn.prepareStatement(ticketQuery);

			// prepare query before executing
			this.prepareQuery(ps, ticketBuild.getSqlColumnNames(), requestMap);
			rs = ps.executeQuery();
			
			//build list of Tickets
			int count = 0;
			int ticketNo = 0;
			TicketVO tvo = null;
			
			while(rs.next()) {
					
				ticketNo = rs.getInt("mbt.id");
	
					if(count != ticketNo){
						//add to list
						if(tvo != null) ticketList.add(tvo);
						//create a new vo
						tvo = new TicketVO(rs);
					}else{
						//add additional data to previous ticket vo
						tvo.getCustomFields().put(rs.getString("customNames"),
								rs.getString("cfs.value"));
					}
					//set count to previous ticket number
					count = ticketNo;
			}
			ticketList.add(tvo);
			log.debug(ticketList.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null)
				closeDBConn();
			if (ps != null) {
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		//sort the list
		Collections.sort(ticketList);
		
		log.debug(ticketList.toString());	
		return ticketList;

	}

	/* (non-Javadoc)
	 * @see com.smt.mantis.procedure.ProcedureInterface#retrieve()
	 */
	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		
	}
}
