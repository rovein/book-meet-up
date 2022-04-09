package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.bookmeetup.dto.OfficeBuildingDto;
import ua.nure.bookmeetup.service.OfficeBuildingService;

import javax.validation.Valid;

import static ua.nure.bookmeetup.validation.BindingResultValidator.errorBody;

@CrossOrigin
@RestController
@RequestMapping("/office-buildings")
@Api(tags = "4. Office Building")
public class OfficeBuildingController {

    private final OfficeBuildingService officeBuildingService;

    @Autowired
    public OfficeBuildingController(OfficeBuildingService officeBuildingService) {
        this.officeBuildingService = officeBuildingService;
    }

    @GetMapping
    @ApiOperation(value = "Returns all office buildings", nickname = "getAllOfficeBuildings")
    public ResponseEntity<?> getAllOfficeBuildings() {
        return ResponseEntity.ok(officeBuildingService.findAllOfficeBuildings());
    }

    @PostMapping
    @ApiOperation(value = "Adds new office building", nickname = "addOfficeBuilding")
    public ResponseEntity<?> addOfficeBuilding(@Valid @RequestBody OfficeBuildingDto officeBuildingDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(officeBuildingService.addOfficeBuilding(officeBuildingDto));
    }

    @PutMapping
    @ApiOperation(value = "Updates office building", nickname = "updateOfficeBuilding")
    public ResponseEntity<?> updateOfficeBuilding(@Valid @RequestBody OfficeBuildingDto officeBuildingDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(errorBody(bindingResult));
        }
        return ResponseEntity.ok(officeBuildingService.updateOfficeBuilding(officeBuildingDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes office building by ID", nickname = "deleteOfficeBuilding")
    public ResponseEntity<Void> deleteOfficeBuilding(@PathVariable Long id) {
        officeBuildingService.deleteOfficeBuilding(officeBuildingService.findOfficeBuildingById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds office building by id", nickname = "getOfficeBuildingById")
    public ResponseEntity<?> getOfficeBuildingById(@PathVariable Long id) {
        return ResponseEntity.ok(officeBuildingService.findOfficeBuildingById(id));
    }

}
