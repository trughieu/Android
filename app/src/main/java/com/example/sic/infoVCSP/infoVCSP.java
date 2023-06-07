package com.example.sic.infoVCSP;

import static com.example.sic.Encrypt.decrypt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.Performed;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;
import vn.mobileid.tse.model.connector.plugin.Attribute;
import vn.mobileid.tse.model.connector.plugin.Response;

public class infoVCSP extends DefaultActivity {

    LinearLayout documentLayout, fullNameLayout, dateBirthLayout, genderLayout, nationalityLayout, ethnicLayout, religionLayout,
            placeOriginLayout, placeResidenceLayout,
            personalIdentityLayout, issuanceDateLayout, expiryDateLayout, oldDocumentNumberLayout, nameFatherLayout,
            nameMotherLayout, nameSpouseLayout, mrzLayout,
            pictureLayout, evidenceLayout, emailLayout, phoneLayout, agreementUUIDLayout, loaLayout;
    TextView optionalInformation, mandaInfo, message, messageCaption,
            IpAddress, Application, RP, Device, btn_Cancel, txt_select_id, btnConfirm;
    String transactionVC, transactionID, idTransaction;
    RequestInfoModule module;
    Performed performed;
    LinearLayout layoutManda, layoutOption;
    String otp, text, digit_6;
    boolean authentication, verification, approve;
    ImageView viewDocument, viewFullname, viewDateBirth, viewGender, viewNationality, viewEthnic, viewReligion, viewPlaceOrigin, viewResidence,
            viewPersonalIdent, viewIssuance, viewExpiry, viewOldNumber, viewNameFather, viewNameMother, viewNameSpouse, viewMRZ,
            viewPicture, viewEvidence, viewEmail, viewPhone, viewUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vcsp);
        start();
        optionalInformation = findViewById(R.id.optionalInformation);
        mandaInfo = findViewById(R.id.mandaInfo);
        message = findViewById(R.id.message);
        messageCaption = findViewById(R.id.messageCaption);
        IpAddress = findViewById(R.id.ipAddress);
        Application = findViewById(R.id.application);
        RP = findViewById(R.id.rpName);
        Device = findViewById(R.id.device);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnConfirm = findViewById(R.id.btnConfirm);

        viewDocument = findViewById(R.id.viewDocument);
        viewFullname = findViewById(R.id.viewFullname);
        viewDateBirth = findViewById(R.id.viewBirthDay);
        viewGender = findViewById(R.id.viewGender);
        viewNationality = findViewById(R.id.viewNationality);
        viewEthnic = findViewById(R.id.viewEthnic);
        viewReligion = findViewById(R.id.viewReligion);
        viewPlaceOrigin = findViewById(R.id.viewPlaceOrigin);
        viewResidence = findViewById(R.id.viewPlaceResidence);
        viewPersonalIdent = findViewById(R.id.viewPersonalIden);
        viewIssuance = findViewById(R.id.viewIssuance);
        viewExpiry = findViewById(R.id.viewExpiry);
        viewOldNumber = findViewById(R.id.viewOldNumber);
        viewNameFather = findViewById(R.id.viewNameFather);
        viewNameMother = findViewById(R.id.viewNameMother);
        viewNameSpouse = findViewById(R.id.viewNameSpouse);
        viewMRZ = findViewById(R.id.viewMRZ);
        viewPicture = findViewById(R.id.viewPicture);
        viewEvidence = findViewById(R.id.viewEvidence);
        viewEmail = findViewById(R.id.viewEmail);
        viewPhone = findViewById(R.id.viewPhone);
        viewUUID = findViewById(R.id.viewUUID);


        layoutManda = findViewById(R.id.layoutManda);
        layoutOption = findViewById(R.id.layoutOptionInformation);

        documentLayout = findViewById(R.id.documentLayout);
        fullNameLayout = findViewById(R.id.fullnameLayout);
        dateBirthLayout = findViewById(R.id.birthdayLayout);
        genderLayout = findViewById(R.id.genderLayout);
        nationalityLayout = findViewById(R.id.nationalityLayout);
        ethnicLayout = findViewById(R.id.ethnicLayout);
        religionLayout = findViewById(R.id.religionLayout);
        placeOriginLayout = findViewById(R.id.placeOriginLayout);
        placeResidenceLayout = findViewById(R.id.placeResidenceLayout);
        personalIdentityLayout = findViewById(R.id.personalIdenLayout);
        issuanceDateLayout = findViewById(R.id.issuanceLayout);
        expiryDateLayout = findViewById(R.id.expiryLayout);
        oldDocumentNumberLayout = findViewById(R.id.oldNumberLayout);
        nameFatherLayout = findViewById(R.id.nameFatherLayout);
        nameMotherLayout = findViewById(R.id.nameMotherLayout);
        nameSpouseLayout = findViewById(R.id.nameSpouseLayout);
        mrzLayout = findViewById(R.id.mrzLayout);
        pictureLayout = findViewById(R.id.pictureLayout);
        evidenceLayout = findViewById(R.id.evidenceLayout);
        emailLayout = findViewById(R.id.emailLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        agreementUUIDLayout = findViewById(R.id.agreementUUIDLayout);
        loaLayout = findViewById(R.id.LoaLayout);

        mandaInfo.setVisibility(View.GONE);
        optionalInformation.setVisibility(View.GONE);

        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            otp = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SharedPreferences bool = getSharedPreferences("transaction", MODE_PRIVATE);
        approve = bool.getBoolean("approve", false);

        if (approve) {
            txt_select_id.setText(getResources().getString(R.string.button_user_allow_approve_later));
        }


        if (documentLayout.getParent() != null || fullNameLayout.getParent() != null || dateBirthLayout.getParent() != null || genderLayout.getParent() != null || nationalityLayout.getParent() != null || ethnicLayout.getParent() != null ||
                religionLayout.getParent() != null || placeResidenceLayout.getParent() != null || placeOriginLayout.getParent() != null || personalIdentityLayout.getParent() != null || issuanceDateLayout.getParent() != null || expiryDateLayout.getParent()
                != null || oldDocumentNumberLayout.getParent() != null ||
                nameSpouseLayout.getParent() != null || nameFatherLayout.getParent() != null || nameMotherLayout.getParent() != null || mrzLayout.getParent() != null || pictureLayout.getParent() != null || evidenceLayout.getParent() != null ||
                emailLayout.getParent() != null || phoneLayout.getParent() != null || agreementUUIDLayout.getParent() != null ||
                loaLayout.getParent() != null) {
            ((ViewGroup) documentLayout.getParent()).removeView(documentLayout);
            ((ViewGroup) fullNameLayout.getParent()).removeView(fullNameLayout);
            ((ViewGroup) dateBirthLayout.getParent()).removeView(dateBirthLayout);
            ((ViewGroup) genderLayout.getParent()).removeView(genderLayout);
            ((ViewGroup) nationalityLayout.getParent()).removeView(nationalityLayout);
            ((ViewGroup) ethnicLayout.getParent()).removeView(ethnicLayout);
            ((ViewGroup) religionLayout.getParent()).removeView(religionLayout);
            ((ViewGroup) placeResidenceLayout.getParent()).removeView(placeResidenceLayout);
            ((ViewGroup) placeOriginLayout.getParent()).removeView(placeOriginLayout);
            ((ViewGroup) personalIdentityLayout.getParent()).removeView(personalIdentityLayout);
            ((ViewGroup) issuanceDateLayout.getParent()).removeView(issuanceDateLayout);
            ((ViewGroup) expiryDateLayout.getParent()).removeView(expiryDateLayout);
            ((ViewGroup) oldDocumentNumberLayout.getParent()).removeView(oldDocumentNumberLayout);
            ((ViewGroup) nameSpouseLayout.getParent()).removeView(nameSpouseLayout);
            ((ViewGroup) nameFatherLayout.getParent()).removeView(nameFatherLayout);
            ((ViewGroup) nameMotherLayout.getParent()).removeView(nameMotherLayout);
            ((ViewGroup) mrzLayout.getParent()).removeView(mrzLayout);
            ((ViewGroup) pictureLayout.getParent()).removeView(pictureLayout);
            ((ViewGroup) evidenceLayout.getParent()).removeView(evidenceLayout);
            ((ViewGroup) emailLayout.getParent()).removeView(emailLayout);
            ((ViewGroup) phoneLayout.getParent()).removeView(phoneLayout);
            ((ViewGroup) agreementUUIDLayout.getParent()).removeView(agreementUUIDLayout);
            ((ViewGroup) loaLayout.getParent()).removeView(loaLayout);

        }

        btn_Cancel.setOnClickListener(v -> {
            module.setResponseCancelTransaction(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    Intent intent = new Intent(v.getContext(), HomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).cancelTransaction();
        });

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialog.setContentView(R.layout.select_confirm);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                LinearLayout approveLater = dialog.findViewById(R.id.check_approveLater);
                LinearLayout Bio = dialog.findViewById(R.id.check_biometrics);
                LinearLayout Pin = dialog.findViewById(R.id.check_pin);
                LinearLayout Identity = dialog.findViewById(R.id.check_e_iden);
                LinearLayout Confirm = dialog.findViewById(R.id.check_confirm);
                ImageView close = dialog.findViewById(R.id.close);
                ImageView viewBio = dialog.findViewById(R.id.viewBiometric);
                ImageView viewIdentity = dialog.findViewById(R.id.viewEidentity);
                ImageView viewConfirm = dialog.findViewById(R.id.viewConfirm);
                ImageView viewApproveLater = dialog.findViewById(R.id.viewApprove);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if (performed.getConfirmationPolicy().equals("TAP") && approve) {
                    Bio.setVisibility(View.GONE);
                    Pin.setVisibility(View.GONE);
                    Identity.setVisibility(View.GONE);
                    viewBio.setVisibility(View.GONE);
                    viewIdentity.setVisibility(View.GONE);
                    viewConfirm.setVisibility(View.GONE);
                } else if (performed.getConfirmationPolicy().equals("TAP")) {
                    approveLater.setVisibility(View.GONE);
                    Bio.setVisibility(View.GONE);
                    Pin.setVisibility(View.GONE);
                    Identity.setVisibility(View.GONE);

                    viewBio.setVisibility(View.GONE);
                    viewIdentity.setVisibility(View.GONE);
                    viewApproveLater.setVisibility(View.GONE);

                }
            }
        });


        module = RequestInfoModule.createModule(this);
        idTransaction = getIntent().getStringExtra("transactionId");
        if (idTransaction != null) {
            module.setResponseCRequestInfo(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                stop();
                                performed = new Performed();
                                performed.setConfirmationPolicy(response.getConfirmationPolicy());
                                performed.setMessage(response.getMessage());
                                performed.setMessageCaption(response.getMessageCaption());

                                if (performed.getConfirmationPolicy().equals("TAP") && !approve) {
                                    txt_select_id.setVisibility(View.GONE);
                                    btnConfirm.setVisibility(View.VISIBLE);
                                }

                                String data = response.getRpName();
                                if (data != null) {
                                    try {
                                        JSONObject json = new JSONObject(data);
                                        performed.setRP_NAME((json.getString("RP NAME")));
                                        performed.setIpAddress(json.getString("IP ADDRESS"));
                                        performed.setApplication(json.getString("APPLICATION"));
                                        performed.setDevice(json.getString("DEVICE"));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                for (Attribute attribute : response.getAttributesToReturns()) {
                                    if (attribute.getAttributeType().equals("DOCUMENT_NUMBER")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(documentLayout);
                                        } else {
                                            layoutOption.addView(documentLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("RELIGION")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(religionLayout);
                                        } else {
                                            layoutOption.addView(religionLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("ETHNIC")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(ethnicLayout);
                                        } else {
                                            layoutOption.addView(ethnicLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("PLACE_OF_RESIDENCE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(placeResidenceLayout);
                                        } else {
                                            layoutOption.addView(placeResidenceLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("BIRTH_DATE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(dateBirthLayout);
                                        } else {
                                            layoutOption.addView(dateBirthLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("GENDER")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(genderLayout);
                                        } else {
                                            layoutOption.addView(genderLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("PLACE_OF_ORIGIN")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(placeOriginLayout);
                                        } else {
                                            layoutOption.addView(placeOriginLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("FULLNAME")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(fullNameLayout);
                                        } else {
                                            layoutOption.addView(fullNameLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("PERSONAL_IDENTIFICATION")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(personalIdentityLayout);
                                        } else {
                                            layoutOption.addView(personalIdentityLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("ISSUANCE_DATE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(issuanceDateLayout);
                                        } else {
                                            layoutOption.addView(issuanceDateLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("EXPIRY_DATE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(expiryDateLayout);
                                        } else {
                                            layoutOption.addView(expiryDateLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("OLD_DOCUMENT_NUMBER")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(oldDocumentNumberLayout);
                                        } else {
                                            layoutOption.addView(oldDocumentNumberLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("FULLNAME_OF_MOTHER")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(nameMotherLayout);
                                        } else {
                                            layoutOption.addView(nameMotherLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("PICTURE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(pictureLayout);
                                        } else {
                                            layoutOption.addView(pictureLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("EVIDENCE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(evidenceLayout);
                                        } else {
                                            layoutOption.addView(evidenceLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("EMAIL")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(emailLayout);
                                        } else {
                                            layoutOption.addView(emailLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("PHONE")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(phoneLayout);
                                        } else {
                                            layoutOption.addView(phoneLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("AGREEMENT_UUID")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(agreementUUIDLayout);
                                        } else {
                                            layoutOption.addView(agreementUUIDLayout);
                                        }
                                    }
                                    if (attribute.getAttributeType().equals("LOA")) {
                                        if (attribute.getRequire()) {
                                            layoutManda.addView(loaLayout);
                                        } else {
                                            layoutOption.addView(loaLayout);
                                        }
                                    }


                                    if (layoutManda.getChildCount() > 0) {
                                        mandaInfo.setVisibility(View.VISIBLE);
                                        int childCount = layoutManda.getChildCount();
                                        boolean isMandaHidden = false;

                                        for (int i = 0; i < childCount; i++) {
                                            View childView = layoutManda.getChildAt(i);
                                            if (i == childCount - 1) {
                                                if (!isMandaHidden) {
                                                    if (childView == documentLayout) {
                                                        viewDocument.setVisibility(View.GONE);
                                                    } else if (childView == religionLayout) {
                                                        viewReligion.setVisibility(View.GONE);
                                                    } else if (childView == ethnicLayout) {
                                                        viewEthnic.setVisibility(View.GONE);
                                                    } else if (childView == placeResidenceLayout) {
                                                        viewResidence.setVisibility(View.GONE);
                                                    } else if (childView == dateBirthLayout) {
                                                        viewDateBirth.setVisibility(View.GONE);
                                                    } else if (childView == genderLayout) {
                                                        viewGender.setVisibility(View.GONE);
                                                    } else if (childView == nationalityLayout) {
                                                        viewNationality.setVisibility(View.GONE);
                                                    } else if (childView == placeOriginLayout) {
                                                        viewPlaceOrigin.setVisibility(View.GONE);
                                                    } else if (childView == fullNameLayout) {
                                                        viewFullname.setVisibility(View.GONE);
                                                    } else if (childView == personalIdentityLayout) {
                                                        viewPersonalIdent.setVisibility(View.GONE);
                                                    } else if (childView == issuanceDateLayout) {
                                                        viewIssuance.setVisibility(View.GONE);
                                                    } else if (childView == expiryDateLayout) {
                                                        viewExpiry.setVisibility(View.GONE);
                                                    } else if (childView == oldDocumentNumberLayout) {
                                                        viewOldNumber.setVisibility(View.GONE);
                                                    } else if (childView == nameFatherLayout) {
                                                        viewNameFather.setVisibility(View.GONE);
                                                    } else if (childView == nameMotherLayout) {
                                                        viewNameMother.setVisibility(View.GONE);
                                                    } else if (childView == nameSpouseLayout) {
                                                        viewNameSpouse.setVisibility(View.GONE);
                                                    } else if (childView == mrzLayout) {
                                                        viewMRZ.setVisibility(View.GONE);
                                                    } else if (childView == pictureLayout) {
                                                        viewPicture.setVisibility(View.GONE);
                                                    } else if (childView == evidenceLayout) {
                                                        viewEvidence.setVisibility(View.GONE);
                                                    } else if (childView == emailLayout) {
                                                        viewEmail.setVisibility(View.GONE);
                                                    } else if (childView == phoneLayout) {
                                                        viewPhone.setVisibility(View.GONE);
                                                    } else if (childView == agreementUUIDLayout) {
                                                        viewUUID.setVisibility(View.GONE);
                                                    }
                                                    isMandaHidden = true;
                                                }
                                            } else {
                                                viewDocument.setVisibility(View.VISIBLE);
                                                viewReligion.setVisibility(View.VISIBLE);
                                                viewEthnic.setVisibility(View.VISIBLE);
                                                viewResidence.setVisibility(View.VISIBLE);
                                                viewDateBirth.setVisibility(View.VISIBLE);
                                                viewGender.setVisibility(View.VISIBLE);
                                                viewNationality.setVisibility(View.VISIBLE);
                                                viewPlaceOrigin.setVisibility(View.VISIBLE);
                                                viewFullname.setVisibility(View.VISIBLE);
                                                viewPersonalIdent.setVisibility(View.VISIBLE);
                                                viewIssuance.setVisibility(View.VISIBLE);
                                                viewExpiry.setVisibility(View.VISIBLE);
                                                viewOldNumber.setVisibility(View.VISIBLE);
                                                viewNameFather.setVisibility(View.VISIBLE);
                                                viewNameMother.setVisibility(View.VISIBLE);
                                                viewNameSpouse.setVisibility(View.VISIBLE);
                                                viewMRZ.setVisibility(View.VISIBLE);
                                                viewPicture.setVisibility(View.VISIBLE);
                                                viewEvidence.setVisibility(View.VISIBLE);
                                                viewEmail.setVisibility(View.VISIBLE);
                                                viewPhone.setVisibility(View.VISIBLE);
                                                viewUUID.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    } else {
                                        mandaInfo.setVisibility(View.GONE);
                                    }

                                    if (layoutOption.getChildCount() > 0) {
                                        optionalInformation.setVisibility(View.VISIBLE);
                                        int optionChildCount = layoutOption.getChildCount();
                                        boolean isOptionHidden = false;
                                        for (int i = 0; i < optionChildCount; i++) {
                                            View childView = layoutOption.getChildAt(i);
                                            if (i == optionChildCount - 1) {
                                                if (!isOptionHidden) {
                                                    if (childView == documentLayout) {
                                                        viewDocument.setVisibility(View.GONE);
                                                    } else if (childView == religionLayout) {
                                                        viewReligion.setVisibility(View.GONE);
                                                    } else if (childView == ethnicLayout) {
                                                        viewEthnic.setVisibility(View.GONE);
                                                    } else if (childView == placeResidenceLayout) {
                                                        viewResidence.setVisibility(View.GONE);
                                                    } else if (childView == dateBirthLayout) {
                                                        viewDateBirth.setVisibility(View.GONE);
                                                    } else if (childView == genderLayout) {
                                                        viewGender.setVisibility(View.GONE);
                                                    } else if (childView == nationalityLayout) {
                                                        viewNationality.setVisibility(View.GONE);
                                                    } else if (childView == placeOriginLayout) {
                                                        viewPlaceOrigin.setVisibility(View.GONE);
                                                    } else if (childView == fullNameLayout) {
                                                        viewFullname.setVisibility(View.GONE);
                                                    } else if (childView == personalIdentityLayout) {
                                                        viewPersonalIdent.setVisibility(View.GONE);
                                                    } else if (childView == issuanceDateLayout) {
                                                        viewIssuance.setVisibility(View.GONE);
                                                    } else if (childView == expiryDateLayout) {
                                                        viewExpiry.setVisibility(View.GONE);
                                                    } else if (childView == oldDocumentNumberLayout) {
                                                        viewOldNumber.setVisibility(View.GONE);
                                                    } else if (childView == nameFatherLayout) {
                                                        viewNameFather.setVisibility(View.GONE);
                                                    } else if (childView == nameMotherLayout) {
                                                        viewNameMother.setVisibility(View.GONE);
                                                    } else if (childView == nameSpouseLayout) {
                                                        viewNameSpouse.setVisibility(View.GONE);
                                                    } else if (childView == mrzLayout) {
                                                        viewMRZ.setVisibility(View.GONE);
                                                    } else if (childView == pictureLayout) {
                                                        viewPicture.setVisibility(View.GONE);
                                                    } else if (childView == evidenceLayout) {
                                                        viewEvidence.setVisibility(View.GONE);
                                                    } else if (childView == emailLayout) {
                                                        viewEmail.setVisibility(View.GONE);
                                                    } else if (childView == phoneLayout) {
                                                        viewPhone.setVisibility(View.GONE);
                                                    } else if (childView == agreementUUIDLayout) {
                                                        viewUUID.setVisibility(View.GONE);
                                                    }
                                                }
                                                isOptionHidden = true;
                                            } else {
                                                viewDocument.setVisibility(View.VISIBLE);
                                                viewReligion.setVisibility(View.VISIBLE);
                                                viewEthnic.setVisibility(View.VISIBLE);
                                                viewResidence.setVisibility(View.VISIBLE);
                                                viewDateBirth.setVisibility(View.VISIBLE);
                                                viewGender.setVisibility(View.VISIBLE);
                                                viewNationality.setVisibility(View.VISIBLE);
                                                viewPlaceOrigin.setVisibility(View.VISIBLE);
                                                viewFullname.setVisibility(View.VISIBLE);
                                                viewPersonalIdent.setVisibility(View.VISIBLE);
                                                viewIssuance.setVisibility(View.VISIBLE);
                                                viewExpiry.setVisibility(View.VISIBLE);
                                                viewOldNumber.setVisibility(View.VISIBLE);
                                                viewNameFather.setVisibility(View.VISIBLE);
                                                viewNameMother.setVisibility(View.VISIBLE);
                                                viewNameSpouse.setVisibility(View.VISIBLE);
                                                viewMRZ.setVisibility(View.VISIBLE);
                                                viewPicture.setVisibility(View.VISIBLE);
                                                viewEvidence.setVisibility(View.VISIBLE);
                                                viewEmail.setVisibility(View.VISIBLE);
                                                viewPhone.setVisibility(View.VISIBLE);
                                                viewUUID.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    } else {
                                        optionalInformation.setVisibility(View.GONE);
                                    }
                                }

                                IpAddress.setText(performed.getIpAddress());
                                Device.setText(performed.getDevice());
                                Application.setText(performed.getApplication());
                                RP.setText(performed.getRP_NAME());
                                message.setText(performed.getMessage());
                                messageCaption.setText(performed.getMessageCaption());
                            }
                        }
                    });
                }
            }).setTransactionID(idTransaction);
        }
    }
}