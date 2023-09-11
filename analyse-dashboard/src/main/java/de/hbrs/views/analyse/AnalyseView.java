package de.hbrs.views.analyse;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.hbrs.ConfigurationBuilder;
import de.hbrs.KafkaControl;
import de.hbrs.RestAPI;
import de.hbrs.data.entity.State;
import de.hbrs.data.entity.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@PageTitle("Analyse")
@Route(value = "analyse")
@RouteAlias(value = "")
@Uses(Icon.class)
public class AnalyseView extends Div {

    public AnalyseView() {
        addClassName("analyse-view");
        add(createHeader());
        add(createConfigurationLayout());
        createAnalyseLayout();
        setupResponseListener();
    }

    private Component createHeader() {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H1("Analyse Dashboard"));
        return layout;
    }

    private Map<String, ComboBox> configurationComboBoxes = new HashMap<>();
    private Map<Test, State> testStates = new HashMap<>();

    private Component createConfigurationLayout() {
        Div container = new Div(new H3("Konfiguration wählen:"));

        // configuration comboboxes
        FormLayout formLayout = new FormLayout();
        for (Test test : Test.values()) {
            ComboBox configItem = new ComboBox(test.getDescription(), test.getConfigurations());
            configurationComboBoxes.put(test.name(), configItem);

            formLayout.add(configItem);
        }

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        Button cancel = new Button("Abbrechen");
        Button save = new Button("Speichern");
        Button load = new Button("Gespeicherte Konfiguration laden");

        load.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(load, save, cancel);

        container.add(formLayout);
        container.add(buttonLayout);
        return container;
    }

    VerticalLayout statusElementContainer = new VerticalLayout();

    private void createAnalyseLayout() {
        Div container = new Div(new H3("Analyse:"));
        container.setWidthFull();

        Button start = new Button("Analyse starten");
        start.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        start.addClickListener(buttonClickEvent -> {

            // check empty configuration
            for (ComboBox box : configurationComboBoxes.values()) {
                String value = (String) box.getValue();
                if (value == null) {
                    Notification.show("Die Konfiguration muss vor der Analyse vollständig ausgefüllt sein.");
                    return;
                }
            }

            String config = ConfigurationBuilder.buildConfig(configurationComboBoxes);
            KafkaControl.distributeConfiguration(config);
            Notification.show("Analyse gestartet.");
        });

        container.add(start);
        this.add(container);

        H4 statusHeader = new H4("Status der Analyse:");
        statusHeader.getStyle().set("margin-top", "30px");
        this.add(statusHeader);

        statusElementContainer = new VerticalLayout();
        statusElementContainer.setWidth("650px");
        statusElementContainer.getStyle().set("margin", "0 auto");
        statusElementContainer.setAlignSelf(FlexComponent.Alignment.CENTER);
        //statusElementContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        statusElementContainer.setAlignItems(FlexComponent.Alignment.START);
        //statusElementContainer.setPadding(true);
        //statusElementContainer.getStyle().set("background-color", "gray");
        //statusElementContainer.setAlignItems(FlexComponent.Alignment.STRETCH);

        for (Test test : Test.values()) {
            testStates.put(test, State.NOT_STARTED);
            statusElementContainer.add(createAnalyseItem(test, State.NOT_STARTED));
        }
        this.add(statusElementContainer);

        this.add(new Hr());
        this.add(new Text("Total: okay"));
    }

    private HorizontalLayout createAnalyseItem(Test test, State state) {
        HorizontalLayout container = new HorizontalLayout();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        container.setWidthFull();
        container.getStyle().set("margin-bottom", "1px solid");
        container.addClassName(LumoUtility.BorderColor.CONTRAST_30);

        Span taskNumber = new Span(String.valueOf(test.getTestID()));
        //taskNumber.getStyle().set("margin-right", "15px");

        Text descriptionElement = new Text(test.getDescription());

        Span statusHolder = new Span(state.toString());
        statusHolder.getElement().getThemeList().add("badge " + state.getCssStyle());
        statusHolder.setMinWidth("150px");

        container.add(taskNumber, descriptionElement, statusHolder);
        return container;
    }

    private void setupResponseListener() {
        KafkaControl.startStatusConsumer(responseString -> {
            ConfigurationBuilder.ConfigurationResponse response = ConfigurationBuilder.readResponse(responseString);
            for (AnalyseView av : AnalyseView.getLoadedVaadinUIs()) {
                av.updateTestStatus(response.equipmentName(), response.equipmentState());
            }
        });
    }

    private static ArrayList<AnalyseView> loadedUIs = new ArrayList<>();

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        loadedUIs.add(this);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        loadedUIs.remove(this);
    }

    public static ArrayList<AnalyseView> getLoadedVaadinUIs() {
        return loadedUIs;
    }

    public void updateTestStatus(String equipmentName, State state) {
        Test test = Test.getByName(equipmentName);
        if (test != null) {
            testStates.put(test, state);
            rebuildAnalyserTable();
        }
    }

    private void rebuildAnalyserTable() {
        getUI().ifPresent(ui -> ui.access(() -> {
            statusElementContainer.removeAll();

            for (Test test : Test.values()) {
                statusElementContainer.add(createAnalyseItem(test, testStates.get(test)));
            }
            ui.push();
        }));
    }

}
