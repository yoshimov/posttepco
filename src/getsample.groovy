// > groovy -cp "gdata-calendar-2.0.jar;gdata-core-1.0.jar;google-collect-1.0-rc1.jar" -Dhttp.proxyHost=proxy.kawasaki.flab.fujitsu.co.jp -Dhttp.proxyPort=8080 test.groovy

import java.awt.*
import java.awt.image.*
import javax.imageio.*

gifurl="http://www.tepco.co.jp/forecast/html/images/juyo-j.gif"

System.setProperty("http.proxyHost", "proxy.kawasaki.flab.fujitsu.co.jp")
System.setProperty("http.proxyPort", "8080")

GRAPH_WIDTH=22
GRAPH_HEIGHT=220
GRAPH_X=60
GRAPH_Y=285
RATIO=1000.0/(286-247)

//image = Toolkit.getDefaultToolkit().createImage(gifurl)
image = ImageIO.read(new URL(gifurl))
for (int hour = 0; hour < 24; hour ++) {
    height=0
    x = GRAPH_X + hour * GRAPH_WIDTH
    for (int y = GRAPH_Y; y > GRAPH_Y-GRAPH_HEIGHT; y --) {
	rgb = image.getRGB(x, y)
	if (rgb < -0xffff80) {
	    // black line
	    height = y
	}
	// for debug
//	println "($x, $y)=" + rgb
    }
    println "height = $height"
    println "hour=$hour, juyo = " + (GRAPH_Y - height + 1) * RATIO
}
