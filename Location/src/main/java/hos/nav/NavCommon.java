package hos.nav;

/**
 * <p>Title: Source </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 15:40
 */
public interface NavCommon {

    enum GPSType {
        bd09ll("bd09ll"),//（百度经纬度坐标）
        bd09mc("bd09mc"),//（百度墨卡托坐标）
        gcj02("gcj02"),//（经国测局加密的坐标）
        wgs84("wgs84");//（gps获取的原始坐标）

        // 成员变量
        private String name;

        GPSType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    enum NavMode {
        transit("transit", 1),//（公交）
        driving("driving", 0),//（驾车）
        walking("walking", 2),//（步行）
        riding("riding", 3),//（骑行）
        train("riding", 4),//（火车）
        intercityBus("riding", 5),//（长途客车）
        ;

        // 成员变量
        private String name;
        private int type;

        NavMode(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public int getType() {
            return type;
        }
    }


}
