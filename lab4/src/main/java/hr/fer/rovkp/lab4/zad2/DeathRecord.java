package hr.fer.rovkp.lab4.zad2;

/**
 * Created by Igor Farszky on 5.6.2017..
 */
public class DeathRecord {

    private Integer id;
    private Integer residentStatus;
    private Integer education1989Revision;
    private Integer education2003Revision;
    private Integer educationReportingRlag;
    private Integer monthOfDeath;
    private String sex;
    private Integer ageType;
    private Integer age;
    private Integer ageSubstitutionFlag;
    private Integer ageRecode52;
    private Integer ageRecode27;
    private Integer ageRecode12;
    private Integer infantAgeRecode22;
    private Integer placeOfDeathAndDecedentsStatus;
    private String maritalStatus;
    private Integer dayOfWeekDeath;
    private Integer currenteDataYear;
    private String injuryAtWork;
    private Integer mannerOfDeath;
    private String methodOfDisposition;
    private String autopsy;
    private Integer activityCode;
    private Integer placeOfInjury;
    private String icd10Code;
    private Integer causeRecode358;
    private Integer causeRecode113;
    private Integer infantCauseRecode130;
    private Integer causeRecode39;
    private Integer numberOfEntityAxisConditions;
    private Integer numberOfRecordAxisConditions;
    private Integer race;
    private Integer bridgedRaceFlag;
    private Integer raceImputationFlag;
    private Integer raceRecode3;
    private Integer raceRecode5;
    private Integer hispanicOrigin;
    private Integer hispanicOriginRaceRecode;

    public DeathRecord(String s){

        String[] ss = s.split(",");
        this.id = Integer.parseInt(ss[0]);
        this.residentStatus = Integer.parseInt(ss[1]);
        this.education1989Revision = Integer.parseInt(ss[2]);
        this.education2003Revision = Integer.parseInt(ss[3]);
        this.educationReportingRlag = Integer.parseInt(ss[4]);
        this.monthOfDeath = Integer.parseInt(ss[5]);
        this.sex = ss[6];
        this.ageType = Integer.parseInt(ss[7]);
        this.age = Integer.parseInt(ss[8]);
        this.ageSubstitutionFlag = Integer.parseInt(ss[9]);
        this.ageRecode52 = Integer.parseInt(ss[10]);
        this.ageRecode27 = Integer.parseInt(ss[11]);
        this.ageRecode12 = Integer.parseInt(ss[12]);
        this.infantAgeRecode22 = Integer.parseInt(ss[13]);
        this.placeOfDeathAndDecedentsStatus = Integer.parseInt(ss[14]);
        this.maritalStatus = ss[15];
        this.dayOfWeekDeath = Integer.parseInt(ss[16]);
        this.currenteDataYear = Integer.parseInt(ss[17]);
        this.injuryAtWork = ss[18];
        this.mannerOfDeath = Integer.parseInt(ss[19]);
        this.methodOfDisposition = ss[20];
        this.autopsy = ss[21];
        this.activityCode = Integer.parseInt(ss[22]);
        this.placeOfInjury = Integer.parseInt(ss[23]);
        this.icd10Code = ss[24];
        this.causeRecode358 = Integer.parseInt(ss[25]);
        this.causeRecode113 = Integer.parseInt(ss[26]);
        this.infantCauseRecode130 = Integer.parseInt(ss[27]);
        this.causeRecode39 = Integer.parseInt(ss[28]);
        this.numberOfEntityAxisConditions = Integer.parseInt(ss[29]);
        this.numberOfRecordAxisConditions = Integer.parseInt(ss[30]);
        this.race = Integer.parseInt(ss[31]);
        this.bridgedRaceFlag = Integer.parseInt(ss[32]);
        this.raceImputationFlag = Integer.parseInt(ss[33]);
        this.raceRecode3 = Integer.parseInt(ss[34]);
        this.raceRecode5 = Integer.parseInt(ss[35]);
        this.hispanicOrigin = Integer.parseInt(ss[36]);
        this.hispanicOriginRaceRecode = Integer.parseInt(ss[37]);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResidentStatus() {
        return residentStatus;
    }

    public void setResidentStatus(Integer residentStatus) {
        this.residentStatus = residentStatus;
    }

    public Integer getEducation1989Revision() {
        return education1989Revision;
    }

    public void setEducation1989Revision(Integer education1989Revision) {
        this.education1989Revision = education1989Revision;
    }

    public Integer getEducation2003Revision() {
        return education2003Revision;
    }

    public void setEducation2003Revision(Integer education2003Revision) {
        this.education2003Revision = education2003Revision;
    }

    public Integer getEducationReportingRlag() {
        return educationReportingRlag;
    }

    public void setEducationReportingRlag(Integer educationReportingRlag) {
        this.educationReportingRlag = educationReportingRlag;
    }

    public Integer getMonthOfDeath() {
        return monthOfDeath;
    }

    public void setMonthOfDeath(Integer monthOfDeath) {
        this.monthOfDeath = monthOfDeath;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAgeType() {
        return ageType;
    }

    public void setAgeType(Integer ageType) {
        this.ageType = ageType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeSubstitutionFlag() {
        return ageSubstitutionFlag;
    }

    public void setAgeSubstitutionFlag(Integer ageSubstitutionFlag) {
        this.ageSubstitutionFlag = ageSubstitutionFlag;
    }

    public Integer getAgeRecode52() {
        return ageRecode52;
    }

    public void setAgeRecode52(Integer ageRecode52) {
        this.ageRecode52 = ageRecode52;
    }

    public Integer getAgeRecode27() {
        return ageRecode27;
    }

    public void setAgeRecode27(Integer ageRecode27) {
        this.ageRecode27 = ageRecode27;
    }

    public Integer getAgeRecode12() {
        return ageRecode12;
    }

    public void setAgeRecode12(Integer ageRecode12) {
        this.ageRecode12 = ageRecode12;
    }

    public Integer getInfantAgeRecode22() {
        return infantAgeRecode22;
    }

    public void setInfantAgeRecode22(Integer infantAgeRecode22) {
        this.infantAgeRecode22 = infantAgeRecode22;
    }

    public Integer getPlaceOfDeathAndDecedentsStatus() {
        return placeOfDeathAndDecedentsStatus;
    }

    public void setPlaceOfDeathAndDecedentsStatus(Integer placeOfDeathAndDecedentsStatus) {
        this.placeOfDeathAndDecedentsStatus = placeOfDeathAndDecedentsStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getDayOfWeekDeath() {
        return dayOfWeekDeath;
    }

    public void setDayOfWeekDeath(Integer dayOfWeekDeath) {
        this.dayOfWeekDeath = dayOfWeekDeath;
    }

    public Integer getCurrenteDataYear() {
        return currenteDataYear;
    }

    public void setCurrenteDataYear(Integer currenteDataYear) {
        this.currenteDataYear = currenteDataYear;
    }

    public String getInjuryAtWork() {
        return injuryAtWork;
    }

    public void setInjuryAtWork(String injuryAtWork) {
        this.injuryAtWork = injuryAtWork;
    }

    public Integer getMannerOfDeath() {
        return mannerOfDeath;
    }

    public void setMannerOfDeath(Integer mannerOfDeath) {
        this.mannerOfDeath = mannerOfDeath;
    }

    public String getMethodOfDisposition() {
        return methodOfDisposition;
    }

    public void setMethodOfDisposition(String methodOfDisposition) {
        this.methodOfDisposition = methodOfDisposition;
    }

    public String getAutopsy() {
        return autopsy;
    }

    public void setAutopsy(String autopsy) {
        this.autopsy = autopsy;
    }

    public Integer getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(Integer activityCode) {
        this.activityCode = activityCode;
    }

    public Integer getPlaceOfInjury() {
        return placeOfInjury;
    }

    public void setPlaceOfInjury(Integer placeOfInjury) {
        this.placeOfInjury = placeOfInjury;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    public Integer getCauseRecode358() {
        return causeRecode358;
    }

    public void setCauseRecode358(Integer causeRecode358) {
        this.causeRecode358 = causeRecode358;
    }

    public Integer getCauseRecode113() {
        return causeRecode113;
    }

    public void setCauseRecode113(Integer causeRecode113) {
        this.causeRecode113 = causeRecode113;
    }

    public Integer getInfantCauseRecode130() {
        return infantCauseRecode130;
    }

    public void setInfantCauseRecode130(Integer infantCauseRecode130) {
        this.infantCauseRecode130 = infantCauseRecode130;
    }

    public Integer getCauseRecode39() {
        return causeRecode39;
    }

    public void setCauseRecode39(Integer causeRecode39) {
        this.causeRecode39 = causeRecode39;
    }

    public Integer getNumberOfEntityAxisConditions() {
        return numberOfEntityAxisConditions;
    }

    public void setNumberOfEntityAxisConditions(Integer numberOfEntityAxisConditions) {
        this.numberOfEntityAxisConditions = numberOfEntityAxisConditions;
    }

    public Integer getNumberOfRecordAxisConditions() {
        return numberOfRecordAxisConditions;
    }

    public void setNumberOfRecordAxisConditions(Integer numberOfRecordAxisConditions) {
        this.numberOfRecordAxisConditions = numberOfRecordAxisConditions;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    public Integer getBridgedRaceFlag() {
        return bridgedRaceFlag;
    }

    public void setBridgedRaceFlag(Integer bridgedRaceFlag) {
        this.bridgedRaceFlag = bridgedRaceFlag;
    }

    public Integer getRaceImputationFlag() {
        return raceImputationFlag;
    }

    public void setRaceImputationFlag(Integer raceImputationFlag) {
        this.raceImputationFlag = raceImputationFlag;
    }

    public Integer getRaceRecode3() {
        return raceRecode3;
    }

    public void setRaceRecode3(Integer raceRecode3) {
        this.raceRecode3 = raceRecode3;
    }

    public Integer getRaceRecode5() {
        return raceRecode5;
    }

    public void setRaceRecode5(Integer raceRecode5) {
        this.raceRecode5 = raceRecode5;
    }

    public Integer getHispanicOrigin() {
        return hispanicOrigin;
    }

    public void setHispanicOrigin(Integer hispanicOrigin) {
        this.hispanicOrigin = hispanicOrigin;
    }

    public Integer getHispanicOriginRaceRecode() {
        return hispanicOriginRaceRecode;
    }

    public void setHispanicOriginRaceRecode(Integer hispanicOriginRaceRecode) {
        this.hispanicOriginRaceRecode = hispanicOriginRaceRecode;
    }

    public static boolean isParsable(String s){

        try{
            String[] ss = s.split(",");
            Integer.parseInt(ss[0]);
            Integer.parseInt(ss[1]);
            Integer.parseInt(ss[2]);
            Integer.parseInt(ss[3]);
            Integer.parseInt(ss[4]);
            Integer.parseInt(ss[5]);
            Integer.parseInt(ss[7]);
            Integer.parseInt(ss[8]);
            Integer.parseInt(ss[9]);
            Integer.parseInt(ss[10]);
            Integer.parseInt(ss[11]);
            Integer.parseInt(ss[12]);
            Integer.parseInt(ss[13]);
            Integer.parseInt(ss[14]);
            Integer.parseInt(ss[16]);
            Integer.parseInt(ss[17]);
            Integer.parseInt(ss[19]);
            Integer.parseInt(ss[22]);
            Integer.parseInt(ss[23]);
            Integer.parseInt(ss[25]);
            Integer.parseInt(ss[26]);
            Integer.parseInt(ss[27]);
            Integer.parseInt(ss[28]);
            Integer.parseInt(ss[29]);
            Integer.parseInt(ss[30]);
            Integer.parseInt(ss[31]);
            Integer.parseInt(ss[32]);
            Integer.parseInt(ss[33]);
            Integer.parseInt(ss[34]);
            Integer.parseInt(ss[35]);
            Integer.parseInt(ss[36]);
            Integer.parseInt(ss[37]);
            return true;
        }catch (NumberFormatException e){
            return false;
        }

    }

}
