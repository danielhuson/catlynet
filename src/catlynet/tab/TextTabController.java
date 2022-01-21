/*
 * TextTabController.java Copyright (C) 2022 Daniel H. Huson
 *
 * (Some files contain contributions from other authors, who are then mentioned separately.)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package catlynet.tab;


import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class TextTabController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab;

    @FXML
    private TextArea textArea;

    @FXML
    private VBox vbox;

    @FXML
    void initialize() {
        tabPane.getTabs().remove(tab);
    }

    public Tab getTab() {
        return tab;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public VBox getVbox() {
        return vbox;
    }
}
