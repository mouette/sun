package com.sogeti.sun.demo.business;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class Scenario {
    
    private ConsoMode mode;
    private InstallationMode installationMode;
    private float latitude;
    private float longitude;
    private float azimuth;
    private float tilt;
    private float longueurRaccordement;
    private float puissance;
    private float surface;
    private float prixTotal;
    private float prixPanneaux;
    private float prixInstallation;
    private float prixRaccordement;
    private float prixOmbriere;

    public Scenario() {
    }

    public Scenario(ConsoMode mode, InstallationMode installationMode) {
        this.mode = mode;
        this.installationMode = installationMode;
    }
}
