package com.exerciseBonus.validators;

import com.exerciseBonus.annotations.PasswordValidate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordValidate,String> {
   private String message;
   private int minLength;
   private int maxLength;
   private boolean containsLowercase;
   private boolean containsUppercase;
   private boolean containsDigit;
   private boolean containsSpecialSymbols;

    @Override
    public void initialize(PasswordValidate password) {
        this.message = password.message();
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.containsLowercase = password.containsLowercase();
        this.containsUppercase = password.containsUppercase();
        this.containsDigit = password.containsDigit();
        this.containsSpecialSymbols = password.containsSpecialSymbols();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password.length() < this.minLength || password.length() > this.maxLength){
            System.out.println(message.toString());
            return false;
        }
        Pattern pattern1 = Pattern.compile("[a-z]");
        Matcher matcher = pattern1.matcher(password);
        if(!matcher.find() && this.containsLowercase){
            System.out.println(message.toString());
            return false;
        }
        Pattern pattern2 = Pattern.compile("[A-Z]");
        matcher = pattern2.matcher(password);
        if(!matcher.find() && this.containsUppercase){
            System.out.println(message.toString());
            return false;
        }
        Pattern pattern3 = Pattern.compile("[0-9]");
        matcher = pattern3.matcher(password);
        if(!matcher.find() && this.containsDigit){
            System.out.println(message.toString());
            return false;
        }
        Pattern pattern4 = Pattern.compile("[!@#$%^&*()_+<>?]");
        matcher = pattern4.matcher(password);
        if(!matcher.find() && this.containsSpecialSymbols){
            System.out.println(message.toString());
            return false;
        }

        return true;
    }
}
