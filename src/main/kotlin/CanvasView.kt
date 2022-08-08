import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.Color.hsb


class CanvasView(
    private val model: Model,
    private val controller: Main
) : Pane() {

    //helper function for finding max as double
    private fun findmax(data: MutableList<Int>):Double {
        var maxhei = 0.0
        for(item in data){
            if(item > maxhei){
                maxhei = item.toDouble()
            }
        }
        return maxhei
    }


    private fun draw(data: MutableList<Int>, wid:Double, ht: Double){
        // clear what the pane had
        this.children.clear()
        var canvas = Canvas(wid, ht)
        var gc = canvas.graphicsContext2D
        // initialize
        var relaventHei = 0.0
        // max of the data
        var maxh = findmax(data)
        var xpos = 5.0
        // wid of each bar with the right white space
        var each = (wid-5.0)/data.count()
        // difference of each color as in hsb
        var degunit = 360.0/data.count()
        // record the current color degree
        var deg = 0.0
        // draw rectangles (the bar graph)
        for(item in data){
            gc.fill = hsb( deg,  1.0,  1.0)
            // update color degree
            deg += degunit
            // update the relevant height of this bar
            relaventHei = item/maxh * ht
            gc.fillRect(xpos, ht-relaventHei, each-5.0, relaventHei)
            // update the x position for next bar
            xpos += each
        }

        // draw y-axis and x-axis
        gc.fill = Color.BLACK
        gc.strokeLine(0.0,0.0,0.0, ht)
        gc.strokeLine(0.0,ht,wid, ht)
        //add to pane
        children.add(canvas)
    }

    var dataset = model.allDataSets[model.get_curDataIdx()]
    var mydata =dataset!!.data

    init{

        // the grey rectangle
        border = Border(BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null,BorderWidths(1.0)))
        // if width/height change, the graph will change
        widthProperty().addListener { evt: Observable? -> draw(mydata, width, height)}
        heightProperty().addListener { evt: Observable? -> println("${height}");draw(mydata, width, height)}
        // draw(mydata)
    }

    // is resizable
    override fun isResizable(): Boolean {
        return true
    }
}