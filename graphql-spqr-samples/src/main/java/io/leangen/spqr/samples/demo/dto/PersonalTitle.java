package io.leangen.spqr.samples.demo.dto;

/**
 * Created by loshmee on 27-9-16.
 */
public enum PersonalTitle {
    MR("Mr."),
    MRS("Mrs."),
    MS("Ms.");

    private String titleLiteral;

    PersonalTitle(String titleLiteral) {
        this.titleLiteral=titleLiteral;
    }

    @Override
    public String toString() {
        return this.titleLiteral;
    }
}
