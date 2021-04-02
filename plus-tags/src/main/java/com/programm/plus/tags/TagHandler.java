package com.programm.plus.tags;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TagHandler {

    List<Tag> tags = new ArrayList<>();

    public void tag(Tag tag){
        this.tags.add(tag);
    }

    public boolean hasTag(Tag tag){
        for(int i=0;i<tags.size();i++){
            if(tags.get(i).is(tag)){
                return true;
            }
        }

        return false;
    }

    @Override
    public TagHandler clone() {
        TagHandler tagHandler = new TagHandler();

        tagHandler.tags.addAll(tags);

        return tagHandler;
    }
}
