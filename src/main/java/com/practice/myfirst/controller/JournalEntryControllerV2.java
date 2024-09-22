package com.practice.myfirst.controller;

import com.practice.myfirst.entity.JournalEntry;
import com.practice.myfirst.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
      return journalEntryService.getAll();
    }


    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myid}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myid){
        return journalEntryService.findById(myid).orElse(null);
    }

    @DeleteMapping("/id/{myid}")
    public Boolean deleteJournalEntryById(@PathVariable ObjectId myid){
        journalEntryService.deleteById(myid);
        return true;
    }

//    @PutMapping("/id/{myid}")
//    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myid,@RequestBody JournalEntry newEntry){
//     JournalEntry old =    journalEntryService.findById(myid).orElse(null);
//     if(old != null){
//    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle():old.getTitle());
//    old.setContent(newEntry.getContent()!=null && !newEntry.equals("")? newEntry.getContent(): old.getContent());
//     }
//        journalEntryService.saveEntry(old);
//        return old;
//    }

    @PutMapping("/id/{myid}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(myid).orElse(null);

        // Check if the old entry exists
        if (old != null) {
            // Only update fields if new values are provided
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                old.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                old.setContent(newEntry.getContent());
            }

            // Save the updated journal entry
            journalEntryService.saveEntry(old);
            return old;
        }

        // Return null or handle the case where entry is not found
        return null;
    }

}
