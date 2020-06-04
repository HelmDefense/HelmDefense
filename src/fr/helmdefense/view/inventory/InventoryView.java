package fr.helmdefense.view.inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.view.inventory.item.InventoryItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class InventoryView extends VBox implements Initializable {
	private ObjectProperty<SortOrder> sortOrderProperty;
	private ObjectProperty<SortCriterion> sortCriterionProperty;
	private ObservableList<InventoryItem> items;
	private ToggleGroup toggleGroup;
	
	@FXML
	private TextField search;
	@FXML
	private RadioMenuItem sortOrderAsc;
	@FXML
	private RadioMenuItem sortOrderDesc;
	@FXML
	private RadioMenuItem sortByStatHp;
	@FXML
	private RadioMenuItem sortByStatDmg;
	@FXML
	private RadioMenuItem sortByName;
	@FXML
	private RadioMenuItem sortByNumber;
	@FXML
	private TilePane inventory;
	
	public InventoryView(SortOrder sortOrder, SortCriterion sortCriterion) {
		this.sortOrderProperty = new SimpleObjectProperty<SortOrder>(SortOrder.DESC);
		this.sortCriterionProperty = new SimpleObjectProperty<SortCriterion>(SortCriterion.NUMBER);
		this.items = FXCollections.observableArrayList();
		this.toggleGroup = new ToggleGroup();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("InventoryView.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.setSortOrder(sortOrder);
		this.setSortCriterion(sortCriterion);
	}
	
	public InventoryView() {
		this(SortOrder.DESC, SortCriterion.NUMBER);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.search.textProperty().addListener((obs, o, n) -> this.filter());
		this.sortOrderAsc.setOnAction(e -> this.setSortOrder(SortOrder.ASC));
		this.sortOrderDesc.setOnAction(e -> this.setSortOrder(SortOrder.DESC));
		this.sortByStatHp.setOnAction(e -> this.setSortCriterion(SortCriterion.STAT_HP));
		this.sortByStatDmg.setOnAction(e -> this.setSortCriterion(SortCriterion.STAT_DMG));
		this.sortByName.setOnAction(e -> this.setSortCriterion(SortCriterion.NAME));
		this.sortByNumber.setOnAction(e -> this.setSortCriterion(SortCriterion.NUMBER));

		ChangeListener<Object> cl = (obs, o, n) -> {
			this.sort();
		};
		this.sortOrderProperty.addListener(cl);
		this.sortCriterionProperty.addListener(cl);
		ListChangeListener<InventoryItem> lcl = c -> {
			while (c.next()) {
				if (c.wasAdded())
					for (InventoryItem item : c.getAddedSubList()) {
						item.setToggleGroup(this.toggleGroup);
						item.amountProperty().addListener(cl);
						item.imgProperty().addListener(cl);
						this.inventory.getChildren().add(item);
					}
				if (c.wasRemoved())
					for (InventoryItem item : c.getRemoved()) {
						item.setToggleGroup(null);
						item.amountProperty().removeListener(cl);
						item.imgProperty().removeListener(cl);
						this.inventory.getChildren().remove(item);
					}
			}
			this.sort();
			this.filter();
		};
		this.items.addListener(lcl);
	}
	
	@FXML
	private void reset(ActionEvent event) {
		this.search.clear();
	}
	
	private void sort() {
		FXCollections.sort(this.inventory.getChildren(), this.getSortCriterion().comparator(this.getSortOrder().reverse()));
	}
	
	private void filter() {
		for (Node item : this.inventory.getChildren())
			if (biMatch(normalize(((InventoryItem) item).getImg()), normalize(this.search.getText())))
				item.setVisible(true);
			else
				item.setVisible(false);
	}
	
	private static String normalize(String str) {
		return str.substring(str.lastIndexOf('.') + 1).toLowerCase().replaceAll("\\s+", "").replaceAll("-", "");
	}
	
	private static boolean biMatch(String str1, String str2) {
		return str1.contains(str2) || str2.contains(str1);
	}
	
	public final SortOrder getSortOrder() {
		return this.sortOrderProperty.get();
	}
	
	public final void setSortOrder(SortOrder sortOrder) {
		this.sortOrderProperty.set(sortOrder);
	}
	
	public final ObjectProperty<SortOrder> sortOrderProperty() {
		return this.sortOrderProperty;
	}
	
	public final SortCriterion getSortCriterion() {
		return this.sortCriterionProperty.get();
	}
	
	public final void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterionProperty.set(sortCriterion);
	}
	
	public final ObjectProperty<SortCriterion> sortCriterionProperty() {
		return this.sortCriterionProperty;
	}
	
	public final ObservableList<InventoryItem> getItems() {
		return this.items;
	}
	
	public final InventoryItem getItem(String img) {
		return this.items.stream()
				.filter(item -> item.getImg().equalsIgnoreCase(img))
				.findAny()
				.orElse(null);
	}
	
	public final ToggleGroup getToggleGroup() {
		return this.toggleGroup;
	}
	
	public enum SortCriterion {
		NAME(Comparator.comparing(node -> ((InventoryItem) node).getImg())),
		NUMBER(Comparator.comparing(node -> ((InventoryItem) node).getAmount())),
		STAT_HP(Comparator.comparing(node -> ((InventoryItem) node).getValue().getData().getStats().get(Attribute.HP))),
		STAT_DMG(Comparator.comparing(node -> ((InventoryItem) node).getValue().getData().getStats().get(Attribute.DMG)));
		
		private Comparator<Node> comparator;
		
		private SortCriterion(Comparator<Node> comparator) {
			this.comparator = comparator;
		}
		
		public Comparator<Node> comparator(boolean reverse) {
			return reverse ? this.comparator.reversed() : this.comparator;
		}
		
		public Comparator<Node> comparator() {
			return this.comparator(false);
		}
	}
	
	public enum SortOrder {
		ASC(false),
		DESC(true);
		
		private boolean reverse;
		
		private SortOrder(boolean reverse) {
			this.reverse = reverse;
		}
		
		public boolean reverse() {
			return this.reverse;
		}
	}
}