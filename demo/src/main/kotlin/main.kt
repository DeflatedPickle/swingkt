import com.deflatedpickle.swingkt.api.Compass
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.widget.swing.menu.button
import com.deflatedpickle.swingkt.widget.swing.menu.checkbox
import com.deflatedpickle.swingkt.widget.swing.menu.menu
import com.deflatedpickle.swingkt.widget.swing.menu.menubar
import com.deflatedpickle.swingkt.widget.swing.menu.radiobutton
import com.deflatedpickle.swingkt.widget.swing.slider
import com.deflatedpickle.swingkt.widget.swing.spinner
import com.deflatedpickle.swingkt.widget.swingx.button
import com.deflatedpickle.swingkt.widget.swingx.field
import com.deflatedpickle.swingkt.widget.swingx.frame
import com.deflatedpickle.swingkt.widget.swingx.label
import com.deflatedpickle.swingkt.widget.swingx.list
import com.deflatedpickle.swingkt.widget.swingx.panel
import javax.swing.UIManager

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    frame(Layout.Border()) {
        title = "frame"
        width = 420
        height = 360

        menubar(Constraint.Border(Compass.SOUTH)) {
            menu {
                text = "File"

                button {
                    text = "Open"
                }

                menu {
                    text = "Exit"

                    radiobutton {
                        text = "Now"
                    }

                    radiobutton {
                        text = "Later"
                    }

                    checkbox {
                        text = "Never"
                    }
                }
            }

            button {
                text = "Hello"
            }
        }

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

            slider(Constraint.Flow()) {
                paint {
                    ticks = true
                    track = true
                    labels = true
                }

                snap {
                    ticks = true
                }

                model = Model.BoundedRange.Integer {
                    value = 5
                    extent = 2
                    min = -5
                    max = 10
                }
            }

            list(Constraint.Flow()) {
                model = Model.List.Default<String> {
                    values = listOf(
                        "Blue",
                        "Yellow",
                        "Green",
                    )
                }
            }
        }
    }.show()
}
