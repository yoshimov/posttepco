// > groovy -cp "gdata-calendar-2.0.jar;gdata-core-1.0.jar;google-collect-1.0-rc1.jar" -Dhttp.proxyHost=proxy.kawasaki.flab.fujitsu.co.jp -Dhttp.proxyPort=8080 test.groovy

import java.awt.*
import java.awt.image.*
import javax.imageio.*
import groovyx.net.http.*

gifurl="http://www.tepco.co.jp/forecast/html/images/juyo-j.gif"
formurl="http://spreadsheets.google.com/formResponse?formkey=dFZDM0tJSnZxMWwybzM5WVlOSHNpSHc6MQ"

System.setProperty("http.proxyHost", "proxy.kawasaki.flab.fujitsu.co.jp")
System.setProperty("http.proxyPort", "8080")

GRAPH_WIDTH=22
GRAPH_HEIGHT=220
GRAPH_X=60
GRAPH_Y=285
RATIO=1000.0/(286-247)

calendar = Calendar.getInstance()
// previous hour
calendar.add(Calendar.HOUR_OF_DAY, -1)
hour = calendar.get(Calendar.HOUR_OF_DAY)
date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " + hour

println "Getting juyo data from graph image."
image = ImageIO.read(new URL(gifurl))
height=0
x = GRAPH_X + hour * GRAPH_WIDTH
for (int y = GRAPH_Y; y > GRAPH_Y-GRAPH_HEIGHT; y --) {
    rgb = image.getRGB(x, y)
    if (rgb < -0xffff80) {
	// black line
	height = y
	break
    } else if (rgb > -0xff) {
	// above the line
	height = y + 1
	break
    }
    // for debug
//	println "($x, $y)=" + rgb
}
println "height = $height"
juyo = (GRAPH_Y - height + 1) * RATIO
println "hour=$hour, juyo = " + juyo

if (juyo < 7000 && juyo > 1000) {
    // post only valid data
    println "posting juyo data into spread sheet."
    posturl=formurl + "&entry.0.single=$hour&entry.1.single=$juyo"
    http = new URL(posturl)
    http.getContent()
    // http builder is not working..
    //http = new HTTPBuilder(formurl)
    //data = ['entry.0.single':date, 'entry.1.single':juyo]
    //http.post(body:data)
}
