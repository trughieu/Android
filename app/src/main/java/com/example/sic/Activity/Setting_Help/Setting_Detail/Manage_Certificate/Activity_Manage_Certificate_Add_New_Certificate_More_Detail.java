package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;


import static com.example.sic.Activity.Home.Inbox.Inbox_detail_1.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sic.Activity.Home.Inbox.Inbox_detail_1;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TimeZone;

import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.CertificateUtils;
import vn.mobileid.tse.model.utils.Utils;

public class Activity_Manage_Certificate_Add_New_Certificate_More_Detail extends DefaultActivity {

    FrameLayout btnBack;
    LinearLayout btnClose;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    TextView tv_name, uid, sn_cn_detail, sn_s_p_detail, sn_c_r_detail, in_cn_detail,
            in_ou_detail, in_o_detail, in_l_detail, in_s_p_detail, in_c_r_detail,
            not_valid_before, not_valid_after, public_key_algorithm, public_key_info_parameter,
            public_key_size_detail, public_key_data_detail,
            serial_number, validity_not_valid_before, validity_not_valid_after,
            certificate_authority_critical, certificate_authority_access_method,
            certificate_authority_access_method_2, certificate_authority_uri, certificate_authority_uri_2,
            subject_key_critical, subject_key_key_identify, basic_contraints_critical, basic_contraints_certificate_autho,
            authority_key_critical, authority_key_key_identify, crl_critical, crl_uri, key_usage_critical, key_usage_uri,
            extended_critical, extended_usage, signature_algorithm, signature_parameter, signature_data,
            fingerprint_sha256, fingerprint_sha1, version;

    String credentialID, transactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_more_detail);
        anh_xa();
        start();

        btnBack.setOnClickListener(view -> {
            Intent intent;
            if (inbox == 1) {
                intent = new Intent(this, Inbox_detail_1.class);
            } else {
                intent = new Intent(this, Activity_Manage_Certificate_Add_New_Certificate_Detail.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
    
            intent.putExtra("id", credentialID);
            intent.putExtra("transactionId", transactionID);
           startActivity(intent);
                finish();
        });
        btnClose.setOnClickListener(view -> {
            Intent intent;
            if (inbox == 1) {
                intent = new Intent(
                        this,
                        Inbox_detail_1.class);
            } else {
                intent = new Intent(
                        this,
                        Activity_Manage_Certificate_Add_New_Certificate_Detail.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
    
            intent.putExtra("id", credentialID);
           startActivity(intent);
                finish();
        });


        Response response = (Response) getIntent().getSerializableExtra("response");
        credentialID = getIntent().getStringExtra("id");
        transactionID = getIntent().getStringExtra("transactionId");
        try {

            X509Certificate x509cert;
            x509cert = CertificateUtils.getCertFormBase64(response.getCert().getCertificates().get(0));
            CertificateUtils SubjectDN = CertificateUtils.getCertificateInfoFormString(x509cert.getSubjectDN().toString());
            CertificateUtils IssuerDN = CertificateUtils.getCertificateInfoFormString(x509cert.getIssuerDN().toString());
            RSAKeyParameters publicKeyRestored = (RSAKeyParameters) PublicKeyFactory.createKey(x509cert.getPublicKey().getEncoded());
            Set<String> criticalSet = x509cert.getCriticalExtensionOIDs();

            /**
             * Subject Name
             */

            String uid_SN = SubjectDN.getMap().get("UID");
            String CN_SN = SubjectDN.getMap().get("CN");
            String O_SN = SubjectDN.getMap().get("O");
            String SP_SN = SubjectDN.getMap().get("ST");
            String C_SN = SubjectDN.getMap().get("C");
            /**
             * Issuer Name
             */

            String CN_IN = IssuerDN.getMap().get("CN");
            String OU_IN = IssuerDN.getMap().get("OU");
            String O_IN = IssuerDN.getMap().get("O");
            String L_IN = IssuerDN.getMap().get("L");
            String SP_IN = IssuerDN.getMap().get("ST");
            String C_IN = IssuerDN.getMap().get("C");
            /**
             * Serial Number
             */
            String Serial_Number = x509cert.getSerialNumber().toString();
            /**
             * Validity period
             */
            Date NOT_VALID_AFTER = x509cert.getNotAfter();
            Date NOT_VALID_BEFORE = x509cert.getNotBefore();
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String valid_to = outputFormat.format(NOT_VALID_AFTER);
            String valid_from = outputFormat.format(NOT_VALID_BEFORE);
            outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            /**
             * Public Key Info
             */
            String bb = x509cert.getPublicKey().toString();

            String ALGORITHM = x509cert.getPublicKey().getAlgorithm();

            byte[] data = x509cert.getPublicKey().getEncoded();


            String PUBLIC_KEY_SIZE = String.valueOf(publicKeyRestored.getModulus().bitLength());
            String PUBLIC_KEY_DATA = Utils.printByteToHex(data);

            /**
             * Subject Key Identifier
             */
            String subjectKeyIdentifier = CertificateUtils.getSubjectKeyIdentifier(x509cert);

            /**
             * Basic Contraints
             */
            boolean basiccontraints = CertificateUtils.getBasicConstraintsStringValue(x509cert);

            /**
             * Authority Key Identifier
             */
            String authorizeKeyIdentifier = CertificateUtils.getAuthorizeKeyIdentifier(x509cert);
            /**
             * CRL Distribution Points
             */
            String uri_crl = CertificateUtils.getCrlUrl(x509cert);

            /**
             * Key Usage
             */
            String uri_key_usage = CertificateUtils.getKeyUsage(x509cert);

            /**
             * Extended by Usage
             */
            String usage_extended = CertificateUtils.getExtendedKeyUsage(x509cert);

            /**
             * Singature
             */
            String algo_signature = x509cert.getSigAlgName();
            byte[] param_signature = x509cert.getSigAlgParams();
            byte[] signa_data = x509cert.getSignature();
            String param = Utils.printByteToHex(param_signature);
            String signaturedata = Utils.printByteToHex(signa_data);

            //SIGNATURE
//                    String sigAlgorithm = x509cert.getSigAlgName();
//                    if (sigAlgorithm != null) {
//                        certificate_signature_algorithm.setText(sigAlgorithm);
//                        ((LinearLayout) certificate_signature_algorithm.getParent()).setVisibility(View.VISIBLE);
//                    }


            /**
             * Fingerprints
             */

            String sha256Fingerprint = CertificateUtils.getCertificateSHA256Fingerprint(x509cert);
            String sha1Fingerprint = CertificateUtils.getCertificateSHA1Fingerprint(x509cert);

            /**
             * Version
             */
            int ver = x509cert.getVersion();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    stop();
                    //Certificate Authority Information Access
                    if (criticalSet.contains("1.3.6.1.5.5.7.1.1")) {
                        certificate_authority_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        certificate_authority_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }
                    //SUBJECT KEY IDENTIFIER
                    if (criticalSet.contains("2.5.29.14")) {
                        subject_key_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        subject_key_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }

                    // BASIC CONSTRAINTS
                    if (criticalSet.contains("2.5.29.19")) {
                        basic_contraints_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        basic_contraints_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }

                    //  AUTHORITY KEY IDENTIFIER
                    if (criticalSet.contains("2.5.29.35")) {
                        authority_key_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        authority_key_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }

                    //  CRL DISTRIBUTION POINTS
                    if (criticalSet.contains("2.5.29.31")) {
                        crl_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        crl_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }

                    //  KEY USAGE
                    if (criticalSet.contains("2.5.29.15")) {
                        key_usage_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        key_usage_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }


                    //   EXTENDED KEY USAGE
                    if (criticalSet.contains("2.5.29.37")) {
                        extended_critical.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        extended_critical.setText(getResources().getString(R.string.certificate_no_value));
                    }


                    /**
                     * subject name
                     */
                    tv_name.setText(CN_SN);
                    uid.setText(uid_SN);
                    sn_cn_detail.setText(CN_SN);
                    sn_s_p_detail.setText(SP_SN);
                    sn_c_r_detail.setText(C_SN);
                    /**
                     * issuer name
                     */
                    in_cn_detail.setText(CN_IN);
                    in_s_p_detail.setText(SP_IN);
                    in_c_r_detail.setText(C_IN);
                    in_ou_detail.setText(OU_IN);
                    in_o_detail.setText(O_IN);
                    in_l_detail.setText(L_IN);
                    /**
                     * serial number
                     */
                    serial_number.setText(Serial_Number);
                    /**
                     * validity period
                     */
                    not_valid_after.setText(valid_from);
                    not_valid_before.setText(valid_to);
                    /**
                     * public key info
                     */
                    public_key_data_detail.setText(PUBLIC_KEY_DATA);
                    public_key_size_detail.setText(PUBLIC_KEY_SIZE);
                    public_key_algorithm.setText(ALGORITHM);
                    public_key_info_parameter.setText(getResources().getString(R.string.none));

                    /**
                     * Certificate Authority Information Access
                     */
                    String access;
                    String uri;
                    String[] access_method = {"access1", "access2"};
                    String[] uri_data = {"uri1", "uri2"};
                    TextView[] access_method_data = {certificate_authority_access_method, certificate_authority_access_method_2};
                    TextView[] uri_key_data = {certificate_authority_uri, certificate_authority_uri_2};

                    LinkedHashMap<String, String> accessMethod = CertificateUtils.getAuthorityInfoAccessMethod(x509cert);
                    if (accessMethod != null) {
                        int i = 0;
                        for (String key : accessMethod.keySet()) {
                            access = key;
                            uri = accessMethod.get(key);
                            access_method_data[i].setText(access_method[i]);
                            uri_key_data[i].setText(uri_data[i]);
                            if (i == 0) {
                                access_method_data[0].setText(access);
                                uri_key_data[0].setText(uri);
                            } else if (i == 1) {
                                access_method_data[1].setText(access);
                                uri_key_data[1].setText(uri);
                            }
                            i++;
                        }
                    }
                    /**
                     *Subject key identifier
                     */
                    subject_key_key_identify.setText(subjectKeyIdentifier);

                    /**
                     * Basic contraints
                     */
                    if (basiccontraints == true) {
                        basic_contraints_certificate_autho.setText(getResources().getString(R.string.certificate_yes_value));
                    } else {
                        basic_contraints_certificate_autho.setText(getResources().getString(R.string.certificate_no_value));
                    }
                    /**
                     * authority key identifier
                     */
                    authority_key_key_identify.setText(authorizeKeyIdentifier);
                    /**
                     * CRL distribution point
                     */
                    crl_uri.setText(uri_crl);
                    /**
                     * key usage
                     */
                    key_usage_uri.setText(uri_key_usage);
                    /**
                     * Extended key usage
                     */
                    extended_usage.setText(usage_extended);
                    /**
                     * Signature
                     */
                    signature_algorithm.setText(algo_signature);
                    signature_parameter.setText(getResources().getString(R.string.none));
                    signature_data.setText(signaturedata);

                    fingerprint_sha256.setText(sha256Fingerprint);
                    fingerprint_sha1.setText(sha1Fingerprint);
                    /**
                     * Version
                     */
                    version.setText(String.valueOf(ver));
                    fingerprint_sha1.setText(sha1Fingerprint);


                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    private void anh_xa() {
        tv_name = findViewById(R.id.tv_name);
        validity_not_valid_after = findViewById(R.id.validity_not_valid_after);
        validity_not_valid_before = findViewById(R.id.validity_not_valid_before);
        public_key_algorithm = findViewById(R.id.public_key_info_algorithm);
        certificate_authority_critical = findViewById(R.id.certificate_authority_critical);
        subject_key_critical = findViewById(R.id.subject_key_critical);
        subject_key_key_identify = findViewById(R.id.subject_key_key_identify);
        basic_contraints_critical = findViewById(R.id.basic_contraints_critical);
        basic_contraints_certificate_autho = findViewById(R.id.basic_contraints_certificate_authority);
        authority_key_critical = findViewById(R.id.authority_key_critical);
        authority_key_key_identify = findViewById(R.id.authority_key_key_identify);
        crl_critical = findViewById(R.id.crl_critical);
        crl_uri = findViewById(R.id.crl_uri);
        key_usage_critical = findViewById(R.id.key_usage_critical);
        key_usage_uri = findViewById(R.id.key_usage_uri);
        extended_critical = findViewById(R.id.extended_by_usage_critical);
        extended_usage = findViewById(R.id.extended_by_usage_usage);
        signature_algorithm = findViewById(R.id.signature_algorithm);
        signature_parameter = findViewById(R.id.signature_parameter);
        signature_data = findViewById(R.id.signature_signature_data);
        fingerprint_sha1 = findViewById(R.id.fingerprints_sha_1);
        fingerprint_sha256 = findViewById(R.id.fingerprints_sha256);
        version = findViewById(R.id.version);
        btnBack = findViewById(R.id.btnBack);
        btnClose = findViewById(R.id.btn_Close);
        uid = findViewById(R.id.uid_detail);
        sn_cn_detail = findViewById(R.id.sn_cn_detail);
        sn_s_p_detail = findViewById(R.id.sn_s_p_detail);
        sn_c_r_detail = findViewById(R.id.sn_c_r_detail);
        in_cn_detail = findViewById(R.id.in_cn_detail);
        in_ou_detail = findViewById(R.id.in_ou_detail);
        in_o_detail = findViewById(R.id.in_o_detail);
        in_l_detail = findViewById(R.id.in_l_detail);
        in_s_p_detail = findViewById(R.id.in_s_p_detail);
        in_c_r_detail = findViewById(R.id.in_c_r_detail);
        not_valid_after = findViewById(R.id.validity_not_valid_after);
        not_valid_before = findViewById(R.id.validity_not_valid_before);
        public_key_info_parameter = findViewById(R.id.public_key_info_parameter);
        public_key_size_detail = findViewById(R.id.public_key_info_key_size);
        public_key_data_detail = findViewById(R.id.public_key_info_key_data);
        certificate_authority_access_method = findViewById(R.id.certificate_authority_access_method);
        certificate_authority_access_method_2 = findViewById(R.id.certificate_authority_access_method_2);
        certificate_authority_uri = findViewById(R.id.certificate_authority_uri);
        certificate_authority_uri_2 = findViewById(R.id.certificate_authority_uri_2);
        serial_number = findViewById(R.id.serial_number_detail);
    }
}