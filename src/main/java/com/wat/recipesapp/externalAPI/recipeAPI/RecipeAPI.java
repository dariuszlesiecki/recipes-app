package com.wat.recipesapp.externalAPI.recipeAPI;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RecipeAPI {
    private String id;
    private String title;
    private String image;
    @JsonProperty("extendedIngredients")
    private ExtendedIngredients[] extendedIngredients;

    private final String apikey = "apiKey=12a97903387e44c79de8e2e4495e7c82&includeNutrition=true";

    static class ExtendedIngredients{
        @JsonProperty("original")
        private String original;

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        @Override
        public String toString() {
            return original;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ExtendedIngredients[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(ExtendedIngredients[] extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    static class Step{
        @JsonProperty("step")
        private String step;

        @JsonProperty("number")
        private String number;

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Step{" +
                    "step='" + step + '\'' +
                    ", number='" + number + '\'' +
                    '}';
        }
    }

    static class Instruction{
        @JsonProperty("steps")
        private Step[] steps;

        public Step[] getSteps() {
            return steps;
        }

        public void setSteps(Step[] steps) {
            this.steps = steps;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "steps=" + Arrays.toString(steps) +
                    '}';
        }
    }

    public String getDescription(){
        StringBuilder description = new StringBuilder();


        String url = "https://api.spoonacular.com/recipes/"+id+"/analyzedInstructions?"+apikey;
        RestTemplate restTemplate = new RestTemplate();
        Instruction[] instructions = restTemplate.getForObject(url,Instruction[].class);
        for(Instruction instruction: instructions){
            for(Step step: instruction.getSteps()){
                description.append(step.getNumber() +": "+step.getStep()+"\n");
            }
        }
        return description.toString();

    }

    @Override
    public String toString() {
        return "RecipeAPI{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", extendedIngredients=" + Arrays.toString(extendedIngredients) +
                '}';
    }
}
