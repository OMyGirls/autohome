package cn.lwtAR.database;

import com.arta.lib.util.StringUtils;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库操作
 * 
 * @author 王春龙
 * 
 */

public class DB {

	public enum DBName {
		/**
		 * 外部存储数据库
		 */
		//ExSDCard("st.db", PathConstant.getExAppDBDir()),

		/**
		 * 默认数据库（内部）
		 */
		ROOT("st.db", null);

		private String dbName;
		private String dbDir;
		DBName(String dbName, String dir) {
			this.dbName = dbName;
			this.dbDir = dir;
		}

		public String getDbName() {
			return dbName;
		}

		public String getDbDir() {
			return dbDir;
		}
	}

	private static DB instance;
	private Map<DBName, DbManager> dbMap;

	private DB() {
		init();
	}

	private void init() {
		dbMap = new HashMap<DBName, DbManager>();
	}

	public static DB getInstance() {
		if (instance == null) {
			synchronized (DB.class) {
				instance = new DB();
			}
		}
		return instance;
	}

	public DbManager getDBTool() {
		return getDBTool(DBName.ROOT);
	}

	public DbManager getDBTool(DBName dbName) {
		if (dbMap.containsKey(dbName)) {
			return dbMap.get(dbName);
		} else {
			synchronized (DB.class) {
				DbManager.DaoConfig config = new DbManager.DaoConfig();
				config.setDbName(dbName.toString())
						.setDbDir(StringUtils.isEmpty(dbName.getDbDir())? null : new File(dbName.getDbDir()))
						.setDbVersion(2)
						.setDbOpenListener(new DbManager.DbOpenListener() {
							@Override
							public void onDbOpened(DbManager db) {
								// 开启WAL, 对写入加速提升巨大
								db.getDatabase().enableWriteAheadLogging();
							}
						})
						.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
							@Override
							public void onUpgrade(DbManager dbManager, int i, int i1) {
							}
						});
				DbManager db = x.getDb(config);
				return db;
			}
		}
	}
}
