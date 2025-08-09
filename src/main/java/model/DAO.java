package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/avengers?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "Dba@1234";

	public DAO() {
	}

	private Connection connector() {
		Connection con = null;
		try {
			Class.forName(this.driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<HeroJavaBeans> getAllTheHeroes() {
		Connection con = null;
		PreparedStatement retrieve = null;
		ResultSet rs = null;
		ArrayList<HeroJavaBeans> heroesList = new ArrayList<>();
		try {
			con = this.connector();
			String selectAllTheHeroes = "SELECT H.HERO_ID, H.HERO_NAME, H.HERO_STATUS, P.POWER_ID, P.POWER_NAME \r\n"
					+ "FROM HERO_POWERS HP\r\n" + "INNER JOIN\r\n" + "    HEROES H ON HP.HERO_ID = H.HERO_ID\r\n"
					+ "INNER JOIN\r\n" + "    POWERS P ON HP.POWER_ID = P.POWER_ID";
			retrieve = con.prepareStatement(selectAllTheHeroes);

			rs = retrieve.executeQuery();

			while (rs.next()) {
				heroesList.add(new HeroJavaBeans(rs.getInt("HERO_ID"), rs.getString("HERO_NAME"),
						rs.getString("HERO_STATUS"), rs.getInt("POWER_ID"), rs.getString("POWER_NAME")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (retrieve != null)
					retrieve.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return heroesList;
	}

	public void insertNewHero(HeroJavaBeans hero) {
		String insertHero = "INSERT INTO heroes (HERO_NAME, HERO_STATUS) VALUES (?, ?)";
		String insertPower = "INSERT INTO powers (POWER_NAME) VALUES (?)";
		String insertLink = "INSERT INTO HERO_POWERS (HERO_ID, POWER_ID) VALUES (?, ?)";

		Connection con = null;
		PreparedStatement heroStatement = null;
		PreparedStatement powerStatement = null;
		PreparedStatement linkStatement = null;

		try {
			con = this.connector();

			heroStatement = con.prepareStatement(insertHero, Statement.RETURN_GENERATED_KEYS);
			heroStatement.setString(1, hero.getHeroName());
			heroStatement.setString(2, hero.getHeroStatus().toString());

			heroStatement.executeUpdate();

			ResultSet rsHero = heroStatement.getGeneratedKeys();
			int heroId = -1;
			if (rsHero.next()) {
				heroId = rsHero.getInt(1);
			}

			powerStatement = con.prepareStatement(insertPower, Statement.RETURN_GENERATED_KEYS);
			powerStatement.setString(1, hero.getPowerName());

			powerStatement.executeUpdate();

			ResultSet rsPower = powerStatement.getGeneratedKeys();
			int powerId = -1;
			if (rsPower.next()) {
				powerId = rsPower.getInt(1);
			}

			if (heroId != -1 && powerId != -1) {
				linkStatement = con.prepareStatement(insertLink);
				linkStatement.setInt(1, heroId);
				linkStatement.setInt(2, powerId);

				linkStatement.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (linkStatement != null)
					linkStatement.close();
				if (powerStatement != null)
					powerStatement.close();
				if (heroStatement != null)
					heroStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public HeroJavaBeans getHeroById(int heroId) {
		Connection con = null;
		PreparedStatement retrieve = null;
		ResultSet rs = null;
		HeroJavaBeans hero = null;

		try {
			con = this.connector();
			String selectHeroById = "SELECT H.HERO_ID, H.HERO_NAME, H.HERO_STATUS, P.POWER_ID, P.POWER_NAME "
					+ "FROM HERO_POWERS HP " + "INNER JOIN HEROES H ON HP.HERO_ID = H.HERO_ID "
					+ "INNER JOIN POWERS P ON HP.POWER_ID = P.POWER_ID " + "WHERE H.HERO_ID = ?";

			retrieve = con.prepareStatement(selectHeroById);
			retrieve.setInt(1, heroId);

			rs = retrieve.executeQuery();

			if (rs.next()) {
				hero = new HeroJavaBeans(rs.getInt("HERO_ID"), rs.getString("HERO_NAME"), rs.getString("HERO_STATUS"),
						rs.getInt("POWER_ID"), rs.getString("POWER_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (retrieve != null)
					retrieve.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hero;
	}

	public void updateHero(HeroJavaBeans hero) {
		String updateHeroSql = "UPDATE heroes SET HERO_NAME = ?, HERO_STATUS = ? WHERE HERO_ID = ?";
		String updatePowerSql = "UPDATE powers SET POWER_NAME = ? WHERE POWER_ID = ?";

		Connection con = null;
		PreparedStatement updateHeroStatement = null;
		PreparedStatement updatePowerStatement = null;

		try {
			con = this.connector();
			con.setAutoCommit(false);

			updateHeroStatement = con.prepareStatement(updateHeroSql);
			updateHeroStatement.setString(1, hero.getHeroName());
			updateHeroStatement.setString(2, hero.getHeroStatus().toString());
			updateHeroStatement.setInt(3, hero.getHeroId());
			updateHeroStatement.executeUpdate();

			updatePowerStatement = con.prepareStatement(updatePowerSql);
			updatePowerStatement.setString(1, hero.getPowerName());
			updatePowerStatement.setInt(2, hero.getPowerId());
			updatePowerStatement.executeUpdate();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (con != null)
					con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (updateHeroStatement != null)
					updateHeroStatement.close();
				if (updatePowerStatement != null)
					updatePowerStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteHero(int heroId, int powerId) {
		String deleteLinkSql = "DELETE FROM HERO_POWERS WHERE HERO_ID = ? AND POWER_ID = ?";
		String deleteHeroSql = "DELETE FROM heroes WHERE HERO_ID = ?";
		String deletePowerSql = "DELETE FROM powers WHERE POWER_ID = ?";

		Connection con = null;
		PreparedStatement deleteLinkStatement = null;
		PreparedStatement deleteHeroStatement = null;
		PreparedStatement deletePowerStatement = null;

		try {
			con = this.connector();
			con.setAutoCommit(false);

			deleteLinkStatement = con.prepareStatement(deleteLinkSql);
			deleteLinkStatement.setInt(1, heroId);
			deleteLinkStatement.setInt(2, powerId);
			deleteLinkStatement.executeUpdate();

			deleteHeroStatement = con.prepareStatement(deleteHeroSql);
			deleteHeroStatement.setInt(1, heroId);
			deleteHeroStatement.executeUpdate();

			deletePowerStatement = con.prepareStatement(deletePowerSql);
			deletePowerStatement.setInt(1, powerId);
			deletePowerStatement.executeUpdate();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (con != null)
					con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (deleteLinkStatement != null)
					deleteLinkStatement.close();
				if (deleteHeroStatement != null)
					deleteHeroStatement.close();
				if (deletePowerStatement != null)
					deletePowerStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}