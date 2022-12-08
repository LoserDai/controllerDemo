import com.com.df.utils.HttpRequestUtil;
import org.junit.Test;

/**
 * @Author feng.dai
 * @Date 2022/12/8 13:20
 * @Project_Name controllerDemo
 * @Package_Name PACKAGE_NAME
 */

public class TestDemo {

    @Test
    public void test(){
        final String url = "http://114.67.101.195:90/product/query";
        String result = HttpRequestUtil.httpRequest(url, "POST");
        try {
            if (result != null) {
                System.out.println(result.length());
            } else {
                System.out.println("null.....");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
