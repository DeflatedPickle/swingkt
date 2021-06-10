import com.deflatedpickle.swingkt.api.Compass
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.widget.swing.*
import com.deflatedpickle.swingkt.widget.swingx.*
import javax.swing.UIManager

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    frame(Layout.Border()) {
        title = "frame"
        width = 420
        height = 360

        components {
            panel(Constraint.Border(), Layout.Flow()) {
                components {
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

                    textfield(Constraint.Flow()) {
                        text = "Hello"
                        prompt = "Greeting"
                    }
                }
            }
        }
    }.show()
}
