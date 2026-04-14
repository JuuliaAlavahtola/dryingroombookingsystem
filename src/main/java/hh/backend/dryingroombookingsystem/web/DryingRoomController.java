package hh.backend.dryingroombookingsystem.web;

import org.springframework.stereotype.Controller;
import hh.backend.dryingroombookingsystem.domain.DryingRoomRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class DryingRoomController {

    private final DryingRoomRepository dryingRoomRepository;

    public DryingRoomController(DryingRoomRepository dryingRoomRepository) {
        this.dryingRoomRepository = dryingRoomRepository;
    }

    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", dryingRoomRepository.findAll());
        return "dryingrooms";
    }

}
