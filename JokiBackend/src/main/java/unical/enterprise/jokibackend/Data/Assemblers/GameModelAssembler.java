package unical.enterprise.jokibackend.Data.Assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import unical.enterprise.jokibackend.Controller.v1.GameController;
import unical.enterprise.jokibackend.Controller.v1.UserController;
import unical.enterprise.jokibackend.Controller.v1.admin.AdminController;
import unical.enterprise.jokibackend.Data.Dto.GameDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameModelAssembler extends RepresentationModelAssemblerSupport<GameDto, Model<GameDto>> {

    public GameModelAssembler() {
        super(GameController.class, (Class<Model<GameDto>>) (Class<?>) Model.class);
    }

    @Override
    public Model<GameDto> toModel(GameDto gameDto) {
        Model<GameDto> gameModel = new Model<>(gameDto);

        Link selfLink = linkTo(methodOn(GameController.class).getGameById(gameDto.getId())).withSelfRel();
        // Aggiungi il link per aggiungere il gioco al carrello
        Link addToCartLink = linkTo(methodOn(UserController.class).addGameToCart(gameDto.getId())).withRel("addToCart").withType("POST");
        Link removeFromCartLink = linkTo(methodOn(UserController.class).removeGameFromCart(gameDto.getId())).withRel("removeFromCart").withType("DELETE");
        Link showAdminLink = linkTo(methodOn(AdminController.class).getAdminById(gameDto.getId())).withRel("Admin").withType("GET");

        gameModel.add(selfLink);
        gameModel.add(addToCartLink);
        gameModel.add(removeFromCartLink);
        gameModel.add(showAdminLink);

        return gameModel;
    }
}