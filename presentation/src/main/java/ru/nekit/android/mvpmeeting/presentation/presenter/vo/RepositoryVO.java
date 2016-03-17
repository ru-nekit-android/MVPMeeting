package ru.nekit.android.mvpmeeting.presentation.presenter.vo;

/**
 * Created by ru.nekit.android on 04.03.16.
 */
public class RepositoryVO {

    private String repoName;
    private String ownerName;

    public RepositoryVO(String repoName, String ownerName) {
        this.repoName = repoName;
        this.ownerName = ownerName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String toString() {
        return repoName + " : " + ownerName;
    }

}