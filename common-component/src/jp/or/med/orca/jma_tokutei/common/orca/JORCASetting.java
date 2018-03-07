package jp.or.med.orca.jma_tokutei.common.orca;

import java.io.*;
import java.util.Properties;

public class JORCASetting
{

    public JORCASetting(String s)
    {
        orcaKikanNumber = null;
        orcaIpAddress = null;
        orcaPort = null;
        orcaDatabase = null;
        orcaProtocol = null;
        orcaUser = null;
        orcaPassword = null;
        orcaEncode = null;
        nichireseUser = null;
        nichiresePassword = null;
        useOrcaIdDigit = false;
        orcaIdDigit = null;
        orcaKikanNumber = s;
    }

    public String getOrcaIpAddress()
    {
        return orcaIpAddress;
    }

    public void setOrcaIpAddress(String s)
    {
        orcaIpAddress = s;
    }

    public String getOrcaPort()
    {
        return orcaPort;
    }

    public void setOrcaPort(String s)
    {
        orcaPort = s;
    }

    public String getOrcaDatabase()
    {
        return orcaDatabase;
    }

    public void setOrcaDatabase(String s)
    {
        orcaDatabase = s;
    }

    public String getOrcaProtocol()
    {
        return orcaProtocol;
    }

    public void setOrcaProtocol(String s)
    {
        orcaProtocol = s;
    }

    public String getOrcaUser()
    {
        return orcaUser;
    }

    public void setOrcaUser(String s)
    {
        orcaUser = s;
    }

    public String getOrcaPassword()
    {
        return orcaPassword;
    }

    public void setOrcaPassword(String s)
    {
        orcaPassword = s;
    }

    public String getOrcaEncode()
    {
        return orcaEncode;
    }

    public void setOrcaEncode(String s)
    {
        orcaEncode = s;
    }

    public boolean Load()
    {
        boolean flag = false;
        Properties properties = new Properties();
        String s = (new StringBuilder()).append("orca_").append(orcaKikanNumber).append(".properties").toString();
        File file = new File(s);
        if(file.exists())
        {
            try
            {
                properties.load(new FileInputStream((new StringBuilder()).append("orca_").append(orcaKikanNumber).append(".properties").toString()));
            }
            catch(FileNotFoundException filenotfoundexception)
            {
                filenotfoundexception.printStackTrace();
                return false;
            }
            catch(IOException ioexception)
            {
                ioexception.printStackTrace();
                return false;
            }
            orcaIpAddress = properties.getProperty("IpAddress");
            orcaPort = properties.getProperty("Port");
            orcaDatabase = properties.getProperty("Database");
            orcaProtocol = properties.getProperty("Protocol");
            orcaUser = properties.getProperty("User");
            orcaPassword = properties.getProperty("Password");
            nichireseUser = properties.getProperty("NichireseUser");
            nichiresePassword = properties.getProperty("NichiresePassword");
            orcaEncode = properties.getProperty("Encode");
            useOrcaIdDigit = Boolean.parseBoolean(properties.getProperty("UseOrcaIdDigit"));
            orcaIdDigit = properties.getProperty("OrcaIdDigit");
            flag = true;
        }
        return flag;
    }

    public boolean Save()
    {
        Properties properties = new Properties();
        properties.put("IpAddress", orcaIpAddress);
        properties.put("Port", orcaPort);
        properties.put("Database", orcaDatabase);
        properties.put("Protocol", orcaProtocol);
        properties.put("User", orcaUser);
        properties.put("Password", orcaPassword);
        properties.put("NichireseUser", nichireseUser);
        properties.put("NichiresePassword", nichiresePassword);
        properties.put("Encode", orcaEncode);
        properties.put("UseOrcaIdDigit", String.valueOf(useOrcaIdDigit));
        properties.put("OrcaIdDigit", String.valueOf(orcaIdDigit));
        try
        {
            properties.store(new FileOutputStream((new StringBuilder()).append("orca_").append(orcaKikanNumber).append(".properties").toString()), "");
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
            return false;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
            return false;
        }
        return true;
    }

    public String getNichiresePassword()
    {
        return nichiresePassword;
    }

    public void setNichiresePassword(String s)
    {
        nichiresePassword = s;
    }

    public String getNichireseUser()
    {
        return nichireseUser;
    }

    public void setNichireseUser(String s)
    {
        nichireseUser = s;
    }

    public String getOrcaIdDigit()
    {
        return orcaIdDigit;
    }

    public void setOrcaIdDigit(String s)
    {
        orcaIdDigit = s;
    }

    public boolean isUseOrcaIdDigit()
    {
        return useOrcaIdDigit;
    }

    public void setUseOrcaIdDigit(boolean flag)
    {
        useOrcaIdDigit = flag;
    }

    private static final String KEY_ENCODE = "Encode";
    private static final String KEY_NICHIRESE_PASSWORD = "NichiresePassword";
    private static final String KEY_NICHIRESE_USER = "NichireseUser";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_USER = "User";
    private static final String KEY_PROTOCOL = "Protocol";
    private static final String KEY_DATABASE = "Database";
    private static final String KEY_PORT = "Port";
    private static final String KEY_IP_ADDRESS = "IpAddress";
    private static final String FILE_EXTENSION = ".properties";
    private static final String FILE_PREFIX = "orca_";
    private static final String KEY_USE_ORCAID_DIGIT = "UseOrcaIdDigit";
    private static final String KEY_ORCAID_DIGIT = "OrcaIdDigit";
    private String orcaKikanNumber;
    private String orcaIpAddress;
    private String orcaPort;
    private String orcaDatabase;
    private String orcaProtocol;
    private String orcaUser;
    private String orcaPassword;
    private String orcaEncode;
    private String nichireseUser;
    private String nichiresePassword;
    private boolean useOrcaIdDigit;
    private String orcaIdDigit;
}
