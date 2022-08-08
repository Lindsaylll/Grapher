import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import java.text.DecimalFormat


class statView (
    private val model: Model,
    private val controller: Main
) : GridPane(), IView {
    override fun updateView() {
        // abstracting data that current showing
        dataset = model.allDataSets[model.get_curDataIdx()]
        mydata =dataset!!.data

        // calculate the num, min,... and update them to text
        var num = mydata.count();var min = findmin(mydata).toInt();var max = findmax(mydata).toInt();var ave = df.format(findave(mydata)).toDouble();var sm = findsum(mydata).toInt()
        numL.text = "${num}";  miniL.text = "${min}";  maxiL.text = "${max}";  averL.text = "${ave}"; sumL.text = "${sm}"
    }

    var dataset = model.allDataSets[model.get_curDataIdx()]
    var mydata =dataset!!.data
    // setup format for average
    private val df = DecimalFormat("0.0");

    //helper function for finding max
    private fun findmax(data: MutableList<Int>):Double {
        var maxhei = 0.0
        for(item in data){
            if(item > maxhei){
                maxhei = item.toDouble()
            }
        }
        return maxhei
    }
    //helper function for finding min
    private fun findmin(data: MutableList<Int>):Double {
        var maxhei = data[0].toDouble()
        for(item in data){
            if(item < maxhei){
                maxhei = item.toDouble()
            }
        }
        return maxhei
    }
    //helper function for finding average
    private fun findave(data: MutableList<Int>):Double {
        var total = 0.0
        for(item in data){
            total += item.toDouble()

        }
        return total/data.count()
    }
    //helper function for finding sum
    private fun findsum(data: MutableList<Int>):Double {
        var total = 0.0
        for(item in data){
            total += item.toDouble()

        }
        return total
    }
    // names of each variable
    val numL = Label(); val miniL = Label();val maxiL = Label();val averL = Label();val sumL = Label();
    init {
        // abstracting data that current showing
        dataset = model.allDataSets[model.get_curDataIdx()]
        mydata =dataset!!.data
        prefWidth = 125.0
        // initial value
        var num=10; var min =10; var max = 100; var ave = 55.0; var sm = 550
        val numb = Label("Number:"); val mini = Label("Minimum:"); val maxi = Label("Maximum:"); val aver = Label("Average:"); val sum = Label("Sum:")

        //update variable and set up format
        num = mydata.count(); min = findmin(mydata).toInt(); max = findmax(mydata).toInt(); ave = df.format(findave(mydata)).toDouble(); sm = findsum(mydata).toInt()
        numL.text = "${num}";  miniL.text = "${min}";  maxiL.text = "${max}";  averL.text = "${ave}"; sumL.text = "${sm}"
        // add them to a list for easy locating
        val listofrit = mutableListOf<Label>()
        listofrit.add(numb);listofrit.add(mini);listofrit.add(maxi);listofrit.add(aver);listofrit.add(sum);
        listofrit.add(numL);listofrit.add(miniL);listofrit.add(maxiL);listofrit.add(averL);listofrit.add(sumL);
        // locating each label, make them at correct position by set them at (colid,rowid)
        var rowid = 0; var colid = 0;
        for(item in listofrit){
            item.alignment = Pos.CENTER_LEFT
            item.isWrapText = false
            this.add(item, colid,rowid,1,1);
            ++rowid
            if(rowid == 5){
                rowid = 0;
                colid = 1
            }
        }
        //layout
        vgap = 10.0
        hgap = 10.0
        maxHeight = 150.0
        alignment = Pos.CENTER
        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}