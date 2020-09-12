package commitware.ayia.covid19.repositories;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.Country;

public class CountriesData {

    private String[] countriesAfrica = {"Algeria", "Angola", "Benin", "Botswana", "Burkina Faso", "Burundi", "Cabo Verde", "Cameroon", "Central African Republic", "Chad",
            "Comoros", "Congo", "Cote d'Ivoire", "Djibouti", "DRC", "Egypt", "Equatorial Guinea", "Eritrea", "Eswatani", "Ethiopia",
            "Gabon", "Gambia", "Ghana", "Guinea", "Guinea-Bissau", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar",
            "Malawi", "Mali", "Mauritania", "Mauritius", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria", "Rwanda",
            "Soa Tome & Principe", "Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa", "South Sudan", "Sudan", "Tanzania", "Togo",
            "Tunisia", "Uganda", "Zambia", "Zimbabwe"};

    private String[] codeAfrica =  {"","","", "", "", "", "", "", "", "",
            "", "", "", "", "", "eg", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "",
            "", "", "", "ma", "", "", "", "", "ng", "",
            "", "", "", "", "", "za", "", "", "", "",
            "", "", "", ""};


    //Europe
    private String[] countriesEurope = {"Albania", "Andorra", "Austria", "Belarus", "Belgium", "Bosnia", "Bulgaria", "Croatia", "Czechia", "Denmark",
            "Estonia", "Finland", "France", "Germany", "Greece", "Hole See", "Hungary", "Iceland", "Italy", "Latvia",
            "Liechtenstein", "Lithuania", "Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "North Macedonia", "Norway",
            "Poland", "Portugal", "Romania", "Russia", "San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden",
            "Switzerland", "Ukraine", "United Kingdom"};

    private String[] codeEurope = {"", "", "", "", "be", "", "bg", "", "cz", "",
            "", "", "fr", "ge", "gr", "", "hu", "ie", "it", "",
            "", "", "", "", "", "", "", "nl", "", "",
            "pl", "pt", "ro", "ru", "", "rs", "", "si", "", "se",
            "ch", "", "gb"};

    //Asia
    private String[] countriesAsia = {"Afghanistan","Armenia", "Azerbaijan", "Bahrain", "Bangladesh", "Bhutan", "Brunei", "Cambodia", "China", "Cyprus",
            "Georgia","Hong Kong", "India", "Indonesia", "Iran", "Iraq", "Israel", "Japan", "Jordan", "Kazakhstan",
            "Laos", "Lebanon", "Malaysia", "Maldives", "Mongolia", "Myanmar", "Nepal", "North Korea", "Oman", "Pakistan",
            "Philippines", "Qatar", "Saudi Arabia", "Singapore", "South Korea", "Sri Lanka", "Palestine", "Syria", "Tajikistan", "Taiwan",
            "Thailand", "Timor-Leste", "Turkey", "Turkmenistan", "UAE" };

    private String[] codeAsia = {"","", "", "", "", "", "", "", "cn", "",
            "","hk", "in", "", "", "", "il", "", "", "",
            "", "", "my", "", "", "", "", "", "", "",
            "ph", "", "sa", "sq", "kr", "", "", "", "", "tw",
            "th", "", "tr", "", "ae" };

    //North America
    private String[]countriesNorthAmerica = {"Antigua and Barbuda", "Bahamas", "Barbados", "Canada", "Costa Rica", "Cuba", "Dominica", "Dominican Republic", "El Salvador", "Grenada",
            "Guatemala", "Haiti", "Honduras", "Jamaica", "Mexico", "Nicaragua", "Panama", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and Grenadines",
            "Trinidad and Tobago", "USA"};

    private String[]codesNorthAmerica = {"", "", "", "ca", "", "cu", "", "", "", "",
            "", "", "", "", "mx", "", "", "", "", "",
            "", "us"};


    //South America
    private String[]countriesSouthAmerica = {"Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Ecuador", "Guyana", "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela"};
    private String[]codesSouthAmerica   = {"ar", "", "", "", "co", "", "", "", "", "", "", "ve"};


    //Australia/Oceania
    private String[]countriesAutraliaOceanea= {"Australia", "Fiji", "Kiribati", "Marshall Islands", "Micronesia", "Nauru", "New Zealand", "Palau", "Papua New Guinea", "Samoa",
            "Solomon Islands", "Tonga", "Tuvalu", "Vanuatu"};
    private String[]codesAutraliaOceanea= {"au", "", "", "", "", "", "nz", "", "", "",
            "", "", "", ""};



    private List<Country> countryList = new ArrayList<>();

    public List<Country> getCountryList() {

        for (int i= 0; i < countriesAfrica.length; i++)
        {
            Country country = new Country();
            country.setName(countriesAfrica[i]);
            country.setContinent("Africa");
            country.setCode(codeAfrica[i]);
            countryList.add(country);
        }
        for (int i= 0; i < countriesEurope.length; i++)
        {
            Country country = new Country();
            country.setName(countriesEurope[i]);
            country.setContinent("Europe");
            country.setCode(codeEurope[i]);
            countryList.add(country);
        }
        for (int i= 0; i < countriesNorthAmerica.length; i++)
        {
            Country country = new Country();
            country.setName(countriesNorthAmerica[i]);
            country.setContinent("North America");
            country.setCode(codesNorthAmerica[i]);
            countryList.add(country);
        }
        for (int i= 0; i < countriesAsia.length; i++)
        {
            Country country = new Country();
            country.setName(countriesAsia[i]);
            country.setContinent("Asia");
            country.setCode(codeAsia[i]);
            countryList.add(country);
        }
        for (int i= 0; i < countriesSouthAmerica.length; i++)
        {
            Country country = new Country();
            country.setName(countriesSouthAmerica[i]);
            country.setContinent("South America");
            country.setCode(codesSouthAmerica[i]);
            countryList.add(country);
        }
        for (int i= 0; i < countriesAutraliaOceanea.length; i++)
        {
            Country country = new Country();
            country.setName(countriesAutraliaOceanea[i]);
            country.setContinent("Australia/Oceania");
            country.setCode(codesAutraliaOceanea[i]);
            countryList.add(country);
        }

        return countryList;
    }

    public Country getCountryDetails(String name)
    {
        List<Country> countries = getCountryList();
        Country country = new Country();
        for (int i= 0; i<countries.size();i++)
        {
            if (name.equals(countries.get(i).getName()));
            {
                country = countries.get(i);
            }
        }
        return country;
    }
}
