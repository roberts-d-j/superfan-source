package com.techelevator.controller;

import com.techelevator.Services.BandService;
import com.techelevator.Services.ImageService;
import com.techelevator.Services.MessageService;
import com.techelevator.dao.BandDao;
import com.techelevator.dao.ImageDao;
import com.techelevator.model.Band;
import com.techelevator.model.Image;
import com.techelevator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class BandController {

    @Autowired
    private BandDao bandDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ImageService imageService;
    @Autowired
    private BandService bandService;
    @Autowired
    private MessageService messageService;

    @RequestMapping(path = "/bands/{bandName}", method = RequestMethod.GET)
    public Band getBand(@PathVariable String bandName) {
        return bandService.getBandByName(bandName);
    }

    @GetMapping(path = "/bands/id/{bandId}")
    public Band getBandById(@PathVariable int bandId) {
        return bandService.getBandById(bandId);
    }

    @RequestMapping(path = "/bands/create/{userId}", method = RequestMethod.POST)
    public Band createBand(@RequestBody Band bandToAdd, @PathVariable int userId) {
        return bandService.createBand(bandToAdd, userId);
    }

    @PutMapping(path = "/bands/update/{bandId}")
    public Band updateBand(@RequestBody Band bandToUpdate, @PathVariable int bandId) {
        return bandService.updateBand(bandToUpdate, bandId);
    }

    @GetMapping(path = "/bands/search/{searchTerm}")
    public List<Band> getAllBands(@PathVariable String searchTerm) {
        return bandService.getBandsBySimilarName(searchTerm);
    }

    @PostMapping(path = "/bands/{bandId}/gallery")
    public boolean addPhotoToGallery(@RequestParam("file")MultipartFile file, @PathVariable int bandId) {
        try{
            return imageService.addImageToBandGallery(file.getOriginalFilename(), file.getBytes(), bandId);
        }catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image data not found.");
        }
    }

    @PostMapping("/photos")
    public Image uploadPhoto(@RequestParam("file")MultipartFile file) {
        try {
            return imageService.uploadImage(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image data not found.");
        }
    }

    @GetMapping("/photos/{imageId}")
    public Image getPhoto(@PathVariable int imageId) {
        return imageService.getBandImageById(imageId);
    }

    @GetMapping("/coverphoto/{bandId}")
    public Image getBandCoverImage(@PathVariable int bandId) {
        return imageService.getCoverImageByBandId(bandId);
    }

    @GetMapping("/bands/{bandId}/gallery")
    public List<Image> getAllBandImagesByBandId(@PathVariable int bandId) {
        return imageService.getAllBandImagesByBandId(bandId);
    }

    @PostMapping("/coverphoto/{bandId}")
    public Image uploadCoverImageToBand(@RequestParam("file") MultipartFile file, @PathVariable int bandId) {
        try {
            Image imageToLink = imageService.uploadImage(file.getOriginalFilename(), file.getBytes());
            imageDao.addCoverImageToBand(imageToLink.getImageId(), bandId);
            return imageToLink;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image data not found.");
        }
    }

    @RequestMapping(path = "/bands/{bandId}/follow/{userId}", method = RequestMethod.POST)
    public boolean followBand(@PathVariable int userId, @PathVariable int bandId) {
        return bandService.followBand(userId, bandId);
    }

    @GetMapping("/bands/following/{userId}")
    public List<Band> getAllUserFollowedBands(@PathVariable int userId) {
        return bandService.getAllUserFollowedBands(userId);
    }

    @GetMapping("/bands/{bandId}/users/{userId}/isfollowing")
    public boolean isFollowing(@PathVariable int userId, @PathVariable int bandId) {
        return bandService.isFollowing(userId, bandId);
    }

    @DeleteMapping("/bands/{bandId}/unfollow/{userId}")
    public boolean unfollowBand(@PathVariable int userId, @PathVariable int bandId) {
        return bandService.unfollowBand(userId, bandId);
    }

    @PostMapping("/bands/{bandId}/owner/{userId}")
    public boolean setBandOwner(@PathVariable int userId, @PathVariable int bandId) {
        return bandService.setBandOwner(userId, bandId);
    }

    @DeleteMapping("/bands/{bandId}/owner/{userId}")
    public boolean removeBandOwner(@PathVariable int userId, @PathVariable int bandId) {
        return bandService.removeBandOwner(userId, bandId);
    }

    @GetMapping("/bands/{bandId}/owner")
    public int getBandOwnerIdByBandId(@PathVariable int bandId) {
        return bandService.getBandOwnerIdByBandId(bandId);
    }

    @GetMapping("/bands/ownedbands/{userId}")
    public List<Band> getBandsByOwnerId(@PathVariable int userId) {
        return bandService.getBandsByOwnerId(userId);
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/messages/user/{userId}")
    public List<Message> getAllMessagesToUser(@PathVariable int userId) {
        return messageService.getAllMessagesToUser(userId);
    }

    @GetMapping("/messages/band/{bandId}")
    public List<Message> getAllMessagesFromBand(@PathVariable int bandId) {
        return messageService.getAllMessagesFromBand(bandId);
    }

    @PostMapping("/messages/send")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @PutMapping("/messages/user/{userId}/delete/{messageId}")
    public boolean hideMessageForUser(@PathVariable int messageId, @PathVariable int userId) {
        return messageService.hideMessageForUser(messageId, userId);
    }

    @PutMapping("/messages/band/delete/{messageId}")
    public boolean hideMessageForBand(@PathVariable int messageId) {
        return messageService.hideMessageForBand(messageId);
    }

    @GetMapping("/messages/user/{userId}/bybandname")
    public List<Message> getAllMessagesToUserOrderedByBandName(@PathVariable int userId) {
        return messageService.getAllMessagesToUserOrderedByBandName(userId);
    }
}
