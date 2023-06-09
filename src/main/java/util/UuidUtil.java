package util;

import dao.*;
import dao.impl.*;

import java.util.Date;
import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}
//	public static String getUuid(){
//		return UUID.randomUUID().toString().replace("-","");
//	}
	public static Date getCurrentTime() {
		Date date = new Date();
		date.setTime(date.getTime() + 8*60*60*1000);
		return date;
	}

	public static String BW = "BW";
	public static int BWNUM = 0;
	public static String EW = "EW";
	public static int EWNUM = 0;
	public static String GBR = "GBR";
	public static int GBRNUM = 0;
	public static String II = "II";
	public static int IINUM = 0;
	public static String OE = "OE";
	public static int OENUM = 0;
	public static String OW = "OW";
	public static int OWNUM = 0;
	public static String PW = "PW";
	public static int PWNUM = 0;
	public static String PR = "PR";
	public static int PRNUM = 0;
	public static String U = "U";
	public static int UNUM = 0;
	public static String WH = "WH";
	public static int WHNUM = 0;
	public static String WR = "WR";
	public static int WRNUM = 0;
	public static String PRO = "PRO";
	public static int PRONUM = 0;
	public static String IDTT = "ID";
	public static int IDTTNUM = 0;

	public static String getPRO() {
		object_entry_dao objectEntryDao = new object_entry_dao_impl();
		int num = stringToNum(objectEntryDao.getMaxOrderId().get(0).getObject_entry_id());
		return PRO+ "-" + String.valueOf((PRONUM++) + num);
	}

	public static String getBW() {
		borrow_in_dao borrowInDao = new borrow_in_dao_impl();
		int num = stringToNum(borrowInDao.getMaxId().get(0).getBorrow_in_warehouse_id());
		return BW + "-" + String.valueOf((BWNUM++) + num);
	}

	public static String getEW() {
		ex_dao exDao = new ex_dao_impl();
		int num = stringToNum(exDao.getMaxId().get(0).getEx_warehouse_id());
		return EW + "-" + String.valueOf((EWNUM++) + num);
	}

	public static String getGBR() {
		get_or_borrow_dao getOrBorrowDao = new get_or_borrow_dao_impl();
		int num = stringToNum(getOrBorrowDao.getMaxId().get(0).getGet_or_borrow_requisition_id());
		return GBR + "-" + String.valueOf((GBRNUM++) + num);
	}

	public static String getII() {
		item_dao itemDao = new item_dao_impl();
		int num = stringToNum(itemDao.getMaxId().get(0).getObject_id());
		return II + "-" + String.valueOf((IINUM++) + num);
	}

	public static String getOE() {
		object_entry_dao objectEntryDao = new object_entry_dao_impl();
		int num = stringToNum(objectEntryDao.getMaxId().get(0).getObject_entry_id());
		return OE + "-" + String.valueOf((OENUM++) + num);
	}

	public static String getOW() {
		other_in_dao otherInDao = new other_in_dao_impl();
		int num = stringToNum(otherInDao.getMaxId().get(0).getOther_in_warehouse_id());
		return OW + "-" + String.valueOf((OWNUM++) + num);
	}

	public static String getPW() {
		purchase_in_dao purchaseInDao = new purchase_in_dao_impl();
		int num = stringToNum(purchaseInDao.getMaxId().get(0).getPurchase_in_warehouse_id());
		return PW + "-" + String.valueOf((PWNUM++) + num);
	}

	public static String getPR() {
		purchase_requisition_dao purchaseRequisitionDao = new purchase_requisition_dao_impl();
		int num = stringToNum(purchaseRequisitionDao.getMaxId().get(0).getPurchase_requisition_id());
		return PR + "-" + String.valueOf((PRNUM++) + num);
	}

	public static String getU() {
		UserDao userDao = new UserDaoImpl();
		int num = stringToNum(userDao.getMaxId().get(0).getUser_id());
		return U + "-" + String.valueOf((UNUM++) + num);
	}

	public static String getWH() {
		warehouse_Dao warehouseDao = new warehouse_dao_impl();
		int num = stringToNum(warehouseDao.getMaxId().get(0).getWarehouse_id());
		return WH + "-" + String.valueOf((WHNUM++) + num);
	}

	public static String getWR() {
		waste_requisition_dao wasteRequisitionDao = new waste_requisition_dao_impl();
		int num = stringToNum(wasteRequisitionDao.getMaxId().get(0).getWaste_requisition_id());
		return WR + "-" + String.valueOf((WRNUM++) + num);
	}

	public static String getIDTT() {
		UserDao userDao = new UserDaoImpl();
		object_entry_dao objectEntryDao = new object_entry_dao_impl();
		int num = stringToNum(objectEntryDao.getMaxOrderId().get(0).getObject_entry_id());
		return PRO+ "-" + String.valueOf((PRONUM++) + num);
	}


	public static int stringToNum(String s) {
		if (s == null) return 0;
		int num = 1;
		int ten = 1;
		int len = s.length() - 1;
		while (len >= 0) {
			if (s.charAt(len) >= '0' && s.charAt(len) <= '9') {
				num = num + (int)(s.charAt(len) - '0') * ten;
				ten *= 10;
			}
			else break;
			len--;
		}
		System.out.println("UUIDUTIL --- NUM : " + num);
		return num;
	}

}
