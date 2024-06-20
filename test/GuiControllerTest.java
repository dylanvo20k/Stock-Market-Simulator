import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import javax.swing.*;

import controller.GuiController;
import model.MockModel;
import view.GuiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GuiControllerTest {
  private GuiView view;
  private GuiController controller;

  @Before
  public void setup() {
    view = new GuiView();
    controller = new GuiController(view);


  }

//  @Test
//  public void testCreatePortfolio() {
//    controller.new CreatePortfolioListener().actionPerformed(
//            new ActionEvent(view.getCreatePortfolioButton(), ActionEvent.ACTION_PERFORMED, "Create Portfolio"));
//
//    assertEquals("Portfolio created for client: ", view.getResultArea().getText().trim());
//    );
//  }
}