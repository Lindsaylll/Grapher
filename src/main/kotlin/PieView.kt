import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import java.lang.Double.min
import java.text.DecimalFormat

class PieView(
    private val model: Model,
    private val controller: Main
) : Pane(), IView {
    override fun updateView() {

    }


    private fun findsum(data: MutableList<Int>):Double {
        var total = 0.0
        for(item in data){
            total += item.toDouble()

        }
        return total
    }


    private fun draw(data: MutableList<Int>, wid:Double, ht: Double){
//        var wid = width
//        var ht = height
        this.children.clear()
        var sum = findsum(data)
        var canvas = Canvas(wid, ht)
        var gc = canvas.graphicsContext2D
        val df = DecimalFormat("0.0");
        var rd = (min(wid, ht))/1.5
        var degunit = 360.0/data.count()
        var deg = 0.0
        var start =  0.0
        var y = (ht- rd)/2
        var ypos = 20.0
        var xpos = wid/2+ rd/1.5
        for(item in data){
            var relaventDeg = item/sum * 360.0
            gc.fill = Color.hsb(deg, 1.0, 1.0)
            deg += degunit
            gc.fillArc(wid/4, y, rd , rd, start,relaventDeg, ArcType.ROUND)
            gc.fillText("${df.format(relaventDeg/360.0*100.0)}%",xpos ,ypos)
            ypos += 20.0
            start += relaventDeg
        }
//        gc.fill = Color.YELLOW
//        gc.fillOval(1.0,2.0, 80.0,80.0)






        children.add(canvas)
    }






    var dataset = model.allDataSets[model.get_curDataIdx()]
    var mydata =dataset!!.data

    init{

        border = Border(BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, BorderWidths(1.0)))

        widthProperty().addListener { evt: Observable? -> draw(mydata, width, height)}
        heightProperty().addListener { evt: Observable? -> println("${height}");draw(mydata, width, height)}
        // draw(mydata)
    }

    override fun isResizable(): Boolean {
        return true
    }


}