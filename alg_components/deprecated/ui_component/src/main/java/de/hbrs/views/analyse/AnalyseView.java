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
import de.hbrs.RestAPI;
import de.hbrs.data.entity.State;
import de.hbrs.data.entity.Tests;

import java.util.ArrayList;

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
    }

    private Component createHeader() {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H1("Analyse Dashboard"));
        return layout;
    }

    ComboBox comboBox_oil;
    ComboBox comboBox_fuel;
    ComboBox comboBox_monitoring;
    ComboBox comboBox_gearbox;

    private Component createConfigurationLayout() {
        Div container = new Div(new H3("Konfiguration wählen:"));

        // comboboxes
        FormLayout formLayout = new FormLayout();
        comboBox_oil = new ComboBox("Oil System", "Oil replenishment system", "diverter valve for duplex filter");
        comboBox_fuel = new ComboBox("Fuel System", "diverter valve for fuel filter", "monitoring fuel leakage");
        comboBox_monitoring = new ComboBox("Monitoring/Control System", "BlueVision", "New Generation");
        comboBox_gearbox = new ComboBox("Gearbox Options", "Reverse reduction gearbox", "el. actuated", "gearbox mounts", "trolling mode for dead-slow propulsion", "free auxiliary PTO", "hydraulic pump drives");
        formLayout.add(comboBox_oil, comboBox_fuel, comboBox_monitoring, comboBox_gearbox);

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
            RestAPI.sendAnchorStart(comboBox_oil.getValue().toString(), comboBox_fuel.getValue().toString(), comboBox_monitoring.getValue().toString(), comboBox_gearbox.getValue().toString());
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
        statusElementContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        statusElementContainer.setAlignItems(FlexComponent.Alignment.STRETCH);
        //statusElementContainer.setPadding(true);
        //statusElementContainer.getStyle().set("background-color", "gray");
        //statusElementContainer.setAlignItems(FlexComponent.Alignment.STRETCH);

        for (Tests test : Tests.values()) {
            statusElementContainer.add(createAnalyseItem(test.getTestID(), test.getDescription(), test.getState()));
        }

        this.add(statusElementContainer);

        this.add(new Hr());
        this.add(new Text("Total: okay"));
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

    public void updateStatus(int id, State state) {
        for (Tests test : Tests.values()) {
            if (test.getTestID() == id) {
                test.setState(state);
            }
        }

        buildData();
    }

    private void buildData() {
        statusElementContainer.removeAll();

        for (Tests test : Tests.values()) {
            statusElementContainer.add(createAnalyseItem(test.getTestID(), test.getDescription(), test.getState()));
        }
    }

    private HorizontalLayout createAnalyseItem(int number, String description, State state) {
        HorizontalLayout container = new HorizontalLayout();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        container.setWidthFull();
        container.getStyle().set("margin-bottom", "1px solid");
        container.addClassName(LumoUtility.BorderColor.CONTRAST_30);

        Span taskNumber = new Span(String.valueOf(number));
        //taskNumber.getStyle().set("margin-right", "15px");

        Text descriptionElement = new Text(description);
        Span statusHolder = new Span(state.toString());
        statusHolder.getElement().getThemeList().add("badge " + state.getCssStyle());

        container.add(taskNumber, descriptionElement, statusHolder);

        return container;


    }

}
