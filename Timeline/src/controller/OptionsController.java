package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class OptionsController {

    @FXML
    private Spinner<Integer> roundsSpinner; // Componente spinner para definir o número de rodadas
    @FXML
    private Button buttonSave;
    
    private int selectedRounds = 5; // Valor padrão
    private Consumer<Integer> onRoundsSelected; // Callback para passar o valor selecionado

    public void initialize() {
        // Configurar o spinner com valores entre 1 e 20 rodadas
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, selectedRounds);
        roundsSpinner.setValueFactory(valueFactory);

        // Listener para capturar o valor selecionado
        roundsSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            selectedRounds = newValue;
        });
    }

    // Método para definir a callback
    public void setOnRoundsSelected(Consumer<Integer> onRoundsSelected) {
        this.onRoundsSelected = onRoundsSelected;
    }

    // Método para receber o valor atual de rodadas do MenuController
    public void setSelectedRounds(int rounds) {
        selectedRounds = rounds;
        roundsSpinner.getValueFactory().setValue(rounds); // Atualiza o spinner com o valor atual
    }

    // Método chamado ao confirmar as opções
    public void applyOptions() {
        if (onRoundsSelected != null) {
            onRoundsSelected.accept(selectedRounds); // Passa o valor selecionado de volta para o MenuController
        }
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close(); // Fecha a janela de opções
    }
}
