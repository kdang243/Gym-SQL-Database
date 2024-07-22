package src.gym.database;

import src.gym.model.*;
import src.gym.model.Constants.GymConstants;
import src.gym.model.Equipment.Equipment1Model;
import src.gym.model.Equipment.Equipment2Model;
import src.gym.model.Equipment.Equipment3AttModel;
import src.gym.model.FitnessClass.FitnessClass1Model;
import src.gym.model.FitnessClass.FitnessClass2Model;
import src.gym.model.Member.MemberModel;
import src.gym.model.Member.Member3AttModel;
import src.gym.model.MemberAttendsModel;
import src.gym.model.MemberLongCountModel;
import src.gym.model.PersonalTrainer.PersonalTrainer1Model;
import src.gym.model.PersonalTrainer.PersonalTrainer2Model;
import src.gym.model.Session.Session1Model;
import src.gym.model.Session.Session2Model;
import src.gym.model.Staff.Staff1Model;
import src.gym.model.Staff.Staff2Model;
import src.gym.model.Staff.StaffIDModel;
import src.gym.model.Staff.StaffModel;
import src.gym.util.PrintablePreparedStatement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This class handles all database related transactions
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";

	private static final String DB_SETUP_SCRIPT_PATH = "src/gym/sql/scripts/databaseSetup.sql";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private final String TABS = "\t\t";

	private final String NEW_LINE = "\n";

	private final String COLON = ":";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void insertMember(MemberModel model) throws SQLException {
		try {
			String query =
					"INSERT \n" +
					"INTO Member(MID, name, phone, email, clearanceLevel)\n" +
					"VALUES (?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, model.getMID());
			ps.setString(2, model.getName());
			ps.setString(3, model.getPhone());
			ps.setString(4, model.getEmail());
			ps.setInt(5,model.getClearanceLevel());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw new SQLException(e);
		}
	}

	public MemberModel[] getMembersWith(int clearanceLevel) throws SQLException {
		ArrayList<MemberModel> result = new ArrayList<MemberModel>();

		try {
			String query =
					"SELECT *\n" +
					"FROM Member\n" +
					"WHERE clearanceLevel = " + clearanceLevel;

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				MemberModel model = new MemberModel(rs.getInt("MID"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("email"),
						rs.getInt("clearanceLevel"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new MemberModel[result.size()]);
	}

	public void deleteStaff(String SType) throws SQLException {
		try {
			String query = "DELETE FROM STAFF1 WHERE STAFF1.STYPE = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, SType);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + SType + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw new SQLException(e);
		}
	}

	public StaffModel[] getAllStaff() throws SQLException {
		ArrayList<StaffModel> result = new ArrayList<StaffModel>();

		try {
			String query =
						"SELECT *\n" +
								"FROM STAFF1 s1, STAFF2 s2\n" +
								"WHERE s1.SType = s2.SType";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int sid = rs.getInt("SID");
				String name = rs.getString("name");
				String SType = rs.getString("SType");
				String workingHours = rs.getString("workingHours");
				StaffModel model = new StaffModel(sid,SType,name,workingHours);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new StaffModel[result.size()]);
	}

	public MemberModel[] getAllMembers() throws SQLException {
		ArrayList<MemberModel> result = new ArrayList<MemberModel>();

		try {
			String query =
					"SELECT *\n" +
							"FROM Member\n";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				MemberModel model = new MemberModel(rs.getInt("MID"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("email"),
						rs.getInt("clearanceLevel"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new MemberModel[result.size()]);
	}



	public void updateMember(int MID, String phone, String email) throws SQLException {
		try {
			String query =
					"UPDATE Member\n" +
					"SET phone = ?, email = ?\n" +
					"WHERE MID = ?";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(
					connection.prepareStatement(query), query, false
			);

			ps.setString(1, phone);
			ps.setString(2, email);
			ps.setInt(3,MID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Member " + MID + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw new SQLException(e);
		}
	}

	public String projectFrom(String[] attributes, String tableName) throws SQLException {
//		try {
//			String query = buildQuery(attributes,tableName);
//
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ResultSet rs = ps.executeQuery();
//
//			while(rs.next()) {
//				UsesModel model = new UsesModel(rs.getInt("MID"),
//						rs.getInt("EID"));
//				result.add(model);
//			}
//
//			rs.close();
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			throw new SQLException(e);
//		}

//		return result.toArray(new UsesModel[result.size()]);
		return "Projection Not Implemented";
	}

	public String projectFromRequires(String[] attributes) throws SQLException {
		String CLEARANCE_LEVEL = "CLEARANCELEVEL";
		String FNUMBER = "FNUMBER";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(CLEARANCE_LEVEL)) {
//				returnString.append(CLEARANCE_LEVEL).append(TABS);
//			}
//
//			if (attributes[i].equals(FNUMBER)) {
//				returnString.append(FNUMBER).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.REQUIRES_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(CLEARANCE_LEVEL)) {
					returnString.append(CLEARANCE_LEVEL).append(COLON).append(rs.getInt(CLEARANCE_LEVEL)).append(TABS);
				}

				if (attributeSet.contains(FNUMBER)) {
					returnString.append(FNUMBER).append(COLON).append(rs.getInt(FNUMBER)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromUses(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "MID";
		String ATTRIBUTE_2 = "EID";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.USES_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getInt(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromTrains(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "MID";
		String ATTRIBUTE_2 = "SID";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.TRAINS_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getInt(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromWorksOn(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "SID";
		String ATTRIBUTE_2 = "FNUMBER";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();
//
//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.WORKS_ON_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getInt(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	// START HERE
	public String projectFromLeads(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "SID";
		String ATTRIBUTE_2 = "SESSIONDATE";
		String ATTRIBUTE_3 = "STARTTIME";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.LEADS_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getDate(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromUtilizes(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "EID";
		String ATTRIBUTE_2 = "SESSIONDATE";
		String ATTRIBUTE_3 = "STARTTIME";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.UTILIZES_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getDate(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromOccursIn(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "ANUMBER";
		String ATTRIBUTE_2 = "SESSIONDATE";
		String ATTRIBUTE_3 = "STARTTIME";
		String ATTRIBUTE_4 = "ATYPE";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.OCCURS_IN_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getDate(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(COLON).append(rs.getString(ATTRIBUTE_4)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromPersonalTrainer1(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "STYPE";
		String ATTRIBUTE_2 = "WORKINGHOURS";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.PERSONAL_TRAINER1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getString(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromPersonalTrainer2(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "SID";
		String ATTRIBUTE_2 = "NAME";
		String ATTRIBUTE_3 = "STYPE";
		String ATTRIBUTE_4 = "PROGRAM";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.PERSONAL_TRAINER2_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getString(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(COLON).append(rs.getString(ATTRIBUTE_4)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromStaff1(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "STYPE";
		String ATTRIBUTE_2 = "WORKINGHOURS";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.STAFF1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getString(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromStaff2(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "SID";
		String ATTRIBUTE_2 = "STYPE";
		String ATTRIBUTE_3 = "NAME";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.STAFF2_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getString(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromFitnessClass1(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "STARTTIME";
		String ATTRIBUTE_2 = "ENDTIME";
		String ATTRIBUTE_3 = "DURATION";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.FITNESS_CLASS1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getTime(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getTime(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getInt(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromFitnessClass2(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "SESSIONDATE";
		String ATTRIBUTE_2 = "STARTTIME";
		String ATTRIBUTE_3 = "ENDTIME";
		String ATTRIBUTE_4 = "FCTYPE";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.FITNESS_CLASS2_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getDate(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getTime(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(COLON).append(rs.getString(ATTRIBUTE_4)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromAttends(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "MID";
		String ATTRIBUTE_2 = "SESSIONDATE";
		String ATTRIBUTE_3 = "STARTTIME";
		String ATTRIBUTE_4 = "LOCKER";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.ATTENDS_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getDate(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(COLON).append(rs.getInt(ATTRIBUTE_4)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromSession1(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "DURATION";
		String ATTRIBUTE_2 = "STARTTIME";
		String ATTRIBUTE_3 = "ENDTIME";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.SESSION1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getTime(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getTime(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromSession2(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "STARTTIME";
		String ATTRIBUTE_2 = "ENDTIME";
		String ATTRIBUTE_3 = "SESSIONDATE";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.SESSION2_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getTime(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getTime(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getDate(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromEquipment1(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "NAME";
		String ATTRIBUTE_2 = "USAGES";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.EQUIPMENT1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getString(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromEquipment2(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "EID";
		String ATTRIBUTE_2 = "NAME";
		String ATTRIBUTE_3 = "CONDITION";
		String ATTRIBUTE_4 = "ATYPE";
		String ATTRIBUTE_5 = "ANUMBER";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_5)) {
//				returnString.append(ATTRIBUTE_5).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.EQUIPMENT2_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getString(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(COLON).append(rs.getString(ATTRIBUTE_4)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_5)) {
					returnString.append(ATTRIBUTE_5).append(COLON).append(rs.getInt(ATTRIBUTE_5)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromArea(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "ANUMBER";
		String ATTRIBUTE_2 = "ATYPE";
		String ATTRIBUTE_3 = "FNUMBER";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.AREA_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(COLON).append(rs.getInt(ATTRIBUTE_3)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromFloor(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "FNUMBER";
		String ATTRIBUTE_2 = "CAPACITY";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.FLOOR_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(COLON).append(rs.getInt(ATTRIBUTE_2)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromMember(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "MID";
		String ATTRIBUTE_2 = "NAME";
		String ATTRIBUTE_3 = "PHONE";
		String ATTRIBUTE_4 = "EMAIL";
		String ATTRIBUTE_5 = "CLEARANCELEVEL";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_2)) {
//				returnString.append(ATTRIBUTE_2).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_3)) {
//				returnString.append(ATTRIBUTE_3).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_4)) {
//				returnString.append(ATTRIBUTE_4).append(TABS);
//			}
//
//			if (attributes[i].equals(ATTRIBUTE_5)) {
//				returnString.append(ATTRIBUTE_5).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.MEMBER_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(":").append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_2)) {
					returnString.append(ATTRIBUTE_2).append(":").append(rs.getString(ATTRIBUTE_2)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_3)) {
					returnString.append(ATTRIBUTE_3).append(":").append(rs.getString(ATTRIBUTE_3)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_4)) {
					returnString.append(ATTRIBUTE_4).append(":").append(rs.getString(ATTRIBUTE_4)).append(TABS);
				}

				if (attributeSet.contains(ATTRIBUTE_5)) {
					returnString.append(ATTRIBUTE_5).append(":").append(rs.getInt(ATTRIBUTE_5)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	public String projectFromClearance(String[] attributes) throws SQLException {
		String ATTRIBUTE_1 = "LEVEL";

		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = attributes[i].toUpperCase();

//			if (attributes[i].equals(ATTRIBUTE_1)) {
//				returnString.append(ATTRIBUTE_1).append(TABS);
//			}
		}

		Set<String> attributeSet = new HashSet<>(List.of(attributes));

		returnString.append(NEW_LINE);

		try {
			String query = buildQuery(attributes, GymConstants.EQUIPMENT1_TABLE);

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (attributeSet.contains(ATTRIBUTE_1)) {
					returnString.append(ATTRIBUTE_1).append(COLON).append(rs.getInt(ATTRIBUTE_1)).append(TABS);
				}

				returnString.append(NEW_LINE);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return returnString.toString();
	}

	private String buildQuery(String[] attributes, String tableName) {
		StringBuilder query = new StringBuilder("SELECT").append(" ");

		for (int i = 0; i < attributes.length; i++) {
			String upper = attributes[i].toUpperCase();

			query.append(upper).append(" AS ").append(upper);


			if (i < attributes.length - 1) {
				query.append(",");
			}
		}

		query.append(" ").append("FROM").append(" ").append(tableName);

		return query.toString();
	}

//	private String buildQuery(
//			String[] attributes,
//			String tableName1,
//			String tableName2,
//			String foreignKey) {
//		StringBuilder query = new StringBuilder("SELECT").append(" ");
//
//		for (int i = 0; i < attributes.length; i++) {
//			query.append(attributes[i]);
//
//			if (i < attributes.length - 1) {
//				query.append(",");
//			}
//		}
//
//		query.append(" ").append("FROM").append(" ");
//		query.append(tableName1).append(",").append(tableName2);
//		query.append(" ").append("WHERE").append(" ");
//		query.append(tableName1).append(".").append(foreignKey).append("=").append(tableName2).append(".").append(foreignKey);
//
//		return query.toString();
//	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	/*
	 	Reads the databaseSetup.sql file and runs all the commands in the order provided in the file.
	 	databaseSetup.sql first drops all the tables in the database, creates the tables anew, and inserts
	 	information fresh into the database
	 */
	public void databaseSetup() {
		/* REFERENCE: A combination of
			https://www.tutorialspoint.com/how-to-run-sql-script-using-jdbc
			https://mybatis.org/mybatis-3/jacoco/org.apache.ibatis.jdbc/ScriptRunner.java.html

			Reading a file into string:
			https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
		*/

		String[] statements;

		try {
			String dbSetup = Files.readString(Path.of(DB_SETUP_SCRIPT_PATH));
			statements = dbSetup.split(";");

			for (int i = 0; i < statements.length; i++) {
				String query = statements[i].trim();
				PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
				ps.executeUpdate();
				ps.close();
			}

		} catch (Exception e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			System.out.println("TRACE " + Arrays.toString(e.getStackTrace()));
		}

//		MemberModel[] members = getMembersWith(GymConstants.CLEARANCE_LEVEL_1);
//
//		System.out.println("Members with clearance level 1: ");
//		for (int i = 0; i < members.length; i++) {
//			MemberModel member = members[i];
//			System.out.println("MID: " + member.getMID());
//			System.out.println("Name: " + member.getName());
//			System.out.println("Phone: " + member.getPhone());
//			System.out.println("Email: " + member.getEmail());
//			System.out.println("Clearance Level: " + member.getClearanceLevel());
//		}
//
//		StaffModel[] staff = getAllStaff();
//		System.out.println("All Staff Before Delete: ");
//		for (int i = 0; i < staff.length; i++) {
//			StaffModel staffMember = staff[i];
//			System.out.println("SID: " + staffMember.getSID());
//			System.out.println("Name: " + staffMember.getName());
//			System.out.println("Staff Type: " + staffMember.getsType());
//			System.out.println("Working Hours: " + staffMember.getWorkingHours());
//		}
//
//		deleteStaff("Trainer");
//
//		staff = getAllStaff();
//		System.out.println("All Staff After Delete: ");
//		for (int i = 0; i < staff.length; i++) {
//			StaffModel staffMember = staff[i];
//			System.out.println("SID: " + staffMember.getSID());
//			System.out.println("Name: " + staffMember.getName());
//			System.out.println("Staff Type: " + staffMember.getsType());
//			System.out.println("Working Hours: " + staffMember.getWorkingHours());
//		}
//
//		updateMember(3,"442 345 3475", "@gmail.com");
//		updateMember(2,"000 000 0000", "43@gmail.com");
//
//		members = getAllMembers();
//
//		System.out.println("All Members: ");
//		for (int i = 0; i < members.length; i++) {
//			MemberModel member = members[i];
//			System.out.println("MID: " + member.getMID());
//			System.out.println("Name: " + member.getName());
//			System.out.println("Phone: " + member.getPhone());
//			System.out.println("Email: " + member.getEmail());
//			System.out.println("Clearance Level: " + member.getClearanceLevel());
//		}


	}

	public Member3AttModel[] showMembersOnDate(Date sessionDate) throws SQLException {
		ArrayList<Member3AttModel> result = new ArrayList<Member3AttModel>();

		try {
			String query =
					"SELECT DISTINCT m.name, m.phone, m.email\n" +
					"FROM Member m, Attends a\n" +
					// "WHERE m.MID = a.MID AND a.sessionDate = " + "'" + sessionDate + " 00:00:01'";
					"WHERE m.MID = a.MID AND a.sessionDate = " + "TO_DATE('" + sessionDate + " 00:00:01','YYYY-MM-DD HH24:MI:SS')" + "";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			System.out.println("Members On Date " + sessionDate + " Are: ");
			while(rs.next()) {
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				System.out.println(name);
				System.out.println(phone);
				System.out.println(email);

				Member3AttModel model = new Member3AttModel(
						name,
						phone,
						email
				);

				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new Member3AttModel[result.size()]);
	}

	public Equipment3AttModel[] getEquipmentAndTheirQuantities() throws SQLException {
		ArrayList<Equipment3AttModel> result = new ArrayList<Equipment3AttModel>();

		try {
			String query =
					"SELECT condition AS condition, COUNT(*) AS count " +
					"FROM EQUIPMENT2 " +
					"GROUP BY condition";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Equipment3AttModel model = new Equipment3AttModel(
						rs.getString("condition"),
						rs.getInt("count"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new Equipment3AttModel[result.size()]);
	}

	public MemberAttendsModel[] getMembersWithMoreThanOneSessionAndTheirSessionInfo() throws SQLException {
		ArrayList<MemberAttendsModel> result = new ArrayList<MemberAttendsModel>();

		try {
			String query =
					"SELECT m.MID AS MID, COUNT(*) AS count\n " +
					"FROM Member m, Attends a\n " +
					"WHERE m.MID = a.MID AND a.sessionDate > TO_DATE('2023-01-01','YYYY-MM-DD')" +
					"GROUP BY m.MID " +
					"HAVING COUNT(*) > 1";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				MemberAttendsModel model = new MemberAttendsModel(
						rs.getInt("MID"),
						rs.getInt("count")
				);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new MemberAttendsModel[result.size()]);
	}

	public MemberLongCountModel[] getNumberOfLongSessionsMembersWithMoreThanOneLongSessionsHaveHad() throws SQLException {
		ArrayList<MemberLongCountModel> result = new ArrayList<MemberLongCountModel>();

		try {
			String query =
					"SELECT m.MID as MID, COUNT(*) as count\n " +
					"FROM Member m, Attends a, Session1 s1, Session2 s2\n " +
					"WHERE m.MID = a.MID AND a.sessionDate = s2.sessionDate AND a.startTime = s1.startTime AND s1.startTime = s2.startTime AND s1.endTime = s2.endTime " +
					"AND s1.duration > (SELECT avg(duration) FROM Session1 s) " +
					"GROUP BY m.MID " +
					"HAVING COUNT(*) > 1";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				MemberLongCountModel model = new MemberLongCountModel(rs.getInt("MID"),
						rs.getInt("count"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new MemberLongCountModel[result.size()]);
	}

	public StaffIDModel[] getStaffWhoHaveWorkedOnAllFloors() throws SQLException {
		ArrayList<StaffIDModel> result = new ArrayList<StaffIDModel>();

		try {
			String query =
					"SELECT s.SID as SID, s.NAME as name\n " +
					"FROM STAFF2 s\n " +
					"WHERE NOT EXISTS ((SELECT f.FNumber FROM Floor f) MINUS\n " +
					"(SELECT wo.FNumber FROM WorksOn wo WHERE wo.SID = s.SID))";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				StaffIDModel model = new StaffIDModel(rs.getInt("SID"),
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw new SQLException(e);
		}

		return result.toArray(new StaffIDModel[result.size()]);
	}
}
