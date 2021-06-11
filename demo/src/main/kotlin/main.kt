import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.widget.swing.spinner
import com.deflatedpickle.swingkt.widget.swingx.button
import com.deflatedpickle.swingkt.widget.swingx.field
import com.deflatedpickle.swingkt.widget.swingx.frame
import com.deflatedpickle.swingkt.widget.swingx.label
import com.deflatedpickle.swingkt.widget.swingx.panel

fun main() {
    frame(Layout.Border()) {
        title = "frame"
        width = 420
        height = 360

        panel(Constraint.Border(), Layout.Flow()) {
            button(Constraint.Flow()) {
                text = "ok"

                onClick += Listener {
                    println("hello")
                }
            }

            label(Constraint.Flow()) {
                text = "Label"
            }

            spinner(Constraint.Flow()) {
                model = Model.Spinner.Number<Double> {
                    value = 8.0
                    min = -2.0
                    max = 24.0
                    step = 0.2
                }

                onChange += Listener {
                    println("it has changed")
                }
            }

            field(Constraint.Flow()) {
                text = "Hello"
                prompt = "Greeting"
            }
        }
    }.show()
}
