package ro.elo.learning.sords;

import de.elo.ix.client.AclItem;
import de.elo.ix.client.DeleteOptions;
import de.elo.ix.client.DocMask;
import de.elo.ix.client.DocMaskC;
import de.elo.ix.client.FindResult;
import de.elo.ix.client.IXConnFactory;
import de.elo.ix.client.IXConnection;
import de.elo.ix.client.LockC;
import de.elo.ix.client.ObjKey;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ro.elo.core.connect.ConnectionManager.getConnection;

public class SordCRUD {

    public static IXConnection getConnection() throws RemoteException {

        IXConnFactory factory = new IXConnFactory("http://192.168.192.185:9090/ix-elo/ix", "TestApp", "1.0");
        IXConnection iXConnection = factory.create("Administrator", "xor123!@#", null, null);
        return iXConnection;
    }

    public static IXConnection getConnectionRunAs(String userName) throws RemoteException {

        IXConnFactory factory = new IXConnFactory("http://192.168.192.185:9090/ix-elo/ix", "TestApp", "1.0");
        IXConnection iXConnection = factory.create("Administrator", "xor123!@#", null, userName);
        return iXConnection;
    }

    public static int createSord(String parentId, String name, String masca) throws RemoteException {
        Sord sord = getConnection().ix().createSord(parentId, masca, SordC.mbAll);
        sord.setName(name);
        return getConnection().ix().checkinSord(sord, SordC.mbAll, LockC.NO);
    }

    public static Sord readSord(String sordId) throws RemoteException {
        return getConnection().ix().checkoutSord(sordId, SordC.mbAll, LockC.NO);
        //maskId, maskName tipul de dosar/document
        //parentId - parinte
        //name - nume
        //IDAteISO = data creare
        //ownerId, ownerName = persoana care l-a creat
        //ACLItems - drepturi
        /* 1 -Read
         *  2  Write
         *  4 Delete
         *  8 Editare document
         *  16 editare lista cuvinte cheie
         */

    }

    // salvare sord acelasi flag ca la checkout
    public static void checkinSord(Sord sord) throws RemoteException {
        getConnection().ix().checkinSord(sord, SordC.mbAll, LockC.NO);
    }

    public static void updateMetadata(Sord sord, String keyName, String keyValue) {
        for (ObjKey objKey : sord.getObjKeys()) {
            if (objKey.getName().equals(keyName)) {
                objKey.setData(new String[]{keyValue});
            }
        }
    }

    public static DocMask readMask(String maskName) throws RemoteException {
        return getConnection().ix().checkoutDocMask(maskName, DocMaskC.mbAll, LockC.NO);
    }

  

    private String fromArray(String[] data) {

        if (data == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String value : data) {
                sb.append(value);
            }
            return sb.toString();
        }
    }

    public static void deleteSord(IXConnection iXConnection, String parentId, String sordId) throws RemoteException {
        DeleteOptions deleteOptions = new DeleteOptions();
        deleteOptions.setDeleteFinally(true);
        iXConnection.ix().deleteSord(parentId, sordId, LockC.NO, null);
        iXConnection.ix().deleteSord(parentId, sordId, LockC.NO, deleteOptions);
    }

    public String getKey(Sord sord, String keyName) {
        if (sord == null || keyName == null) {
            return null;
        }
        for (ObjKey objKey : sord.getObjKeys()) {
            if (objKey.getName().equals(keyName) && objKey.getData().length > 0) {
                return fromArray(objKey.getData());
            }
        }
        return "";
    }

    //  d.      Atribuim o rezolutie pe un pas din flux
    //  rezolutia se salveaza ca o metadata pe sordul aflat pe flux .
    //  Se face din form cu setKey pe metadata REZOLUTIE 
    //  noi salvam in metadate aceasta e vorba despre ce actiuni veti face voi
    public void setKey(Sord sord, String keyName, String value) {
        if (sord == null || keyName == null) {
            return;
        }
        for (ObjKey objKey : sord.getObjKeys()) {
            if (objKey.getName().equals(keyName)) {
                objKey.setData(value.split("(?<=\\G.{255})"));
            }
        }
    }
    
   

}
