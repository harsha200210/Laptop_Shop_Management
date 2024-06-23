package lk.ijse.Laptop_Shop_Management.bo;

import lk.ijse.Laptop_Shop_Management.bo.custom.impl.*;

public class BOFactory {

    public enum BOType{
        CUSTOMER, DRIVER, EMPLOYEE, ITEM, SUPPLIER, BUYING, BUYINGLIST, DELETEDATA, DELIVERYSETTING, GENERATEQR, LOGIN, MAIN, ORDER, ORDERLIST, OUTOFSTOKE, PAYMENT, SIGNUP, TAXRATE, USERSETTING
    }

    public static SuperBO getBO(BOType daoType){
        switch (daoType){
            case CUSTOMER:
                return new CustomerBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case BUYING:
                return new BuyingBOImpl();
            case BUYINGLIST:
                return new BuyingListBOImpl();
            case DELETEDATA:
                return new DeleteDataBOImpl();
            case DELIVERYSETTING:
                return new DeliverySettingBOImpl();
            case GENERATEQR:
                return new GenerateQrBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case MAIN:
                return new MainBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDERLIST:
                return new OrderListBOImpl();
            case OUTOFSTOKE:
                return new OutOfStokeBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SIGNUP:
                return new SignUpBOImpl();
            case TAXRATE:
                return new TaxRateBOImpl();
            case USERSETTING:
                return new UserSettingBOImpl();
            default:
                return null;
        }
    }
}
