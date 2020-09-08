package com.mydesignerclothing.mobile.android.create.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class CreateContentMessages implements ProguardJsonMappable, Parcelable {

  @Expose
  private String colorDialogTitle;

  private List<String> colorPassengersList;

  @SuppressWarnings("EmptyMethod")
  public CreateContentMessages() {
    //NOSONAR
  }

  protected CreateContentMessages(Parcel in) {
    colorDialogTitle = in.readString();
    colorPassengersList = in.createStringArrayList();
  }

  public static final Creator<CreateContentMessages> CREATOR = new Creator<CreateContentMessages>() {
    @Override
    public CreateContentMessages createFromParcel(Parcel in) {
      return new CreateContentMessages(in);
    }

    @Override
    public CreateContentMessages[] newArray(int size) {
      return new CreateContentMessages[size];
    }
  };

  public String getColorDialogTitle() {
    return colorDialogTitle;
  }

  public List<String> getColorPassengersList() {
    return colorPassengersList;
  }

  public void setColorPassengersList(List<String> exitRowPassengersList) {
    this.colorPassengersList = exitRowPassengersList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(colorDialogTitle);
    parcel.writeStringList(colorPassengersList);
  }

  public void setColorDialogTitle(String colorDialogTitle) {
    this.colorDialogTitle = colorDialogTitle;
  }
}
