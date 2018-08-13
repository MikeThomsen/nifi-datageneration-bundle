package org.apache.nifi.datageneration.templates.faker;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.attributes.AttributeResolver;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.NodeVisitorFactory;
import com.mitchellbosecke.pebble.extension.Test;
import com.mitchellbosecke.pebble.operator.BinaryOperator;
import com.mitchellbosecke.pebble.operator.UnaryOperator;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

import org.apache.nifi.datageneration.templates.faker.functions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakerExtension implements Extension {
    private Faker faker;

    public FakerExtension() {
        this.faker = new Faker();
    }

    @Override
    public Map<String, Filter> getFilters() {
        return null;
    }

    @Override
    public Map<String, Test> getTests() {
        return null;
    }

    @Override
    public Map<String, Function> getFunctions() {
        return new HashMap<String, Function>() {{
            put("address_building_number", new AddressBuildingNumberFunction(faker));
            put("address_city", new AddressCityFunction(faker));
            put("address_city_name", new AddressCityNameFunction(faker));
            put("address_city_prefix", new AddressCityPrefixFunction(faker));
            put("address_city_suffix", new AddressCitySuffixFunction(faker));
            put("address_country", new AddressCountryFunction(faker));
            put("address_country_code", new AddressCountryCodeFunction(faker));
            put("address_first_name", new AddressFirstNameFunction(faker));
            put("address_full_address", new AddressFullAddressFunction(faker));
            put("address_last_name", new AddressLastNameFunction(faker));
            put("address_latitude", new AddressLatitudeFunction(faker));
            put("address_longitude", new AddressLongitudeFunction(faker));
            put("address_secondary_address", new AddressSecondaryAddressFunction(faker));
            put("address_state", new AddressStateFunction(faker));
            put("address_state_abbr", new AddressStateAbbrFunction(faker));
            put("address_street_address", new AddressStreetAddressFunction(faker));
            put("address_street_address_number", new AddressStreetAddressNumberFunction(faker));
            put("address_street_name", new AddressStreetNameFunction(faker));
            put("address_street_prefix", new AddressStreetPrefixFunction(faker));
            put("address_street_suffix", new AddressStreetSuffixFunction(faker));
            put("address_time_zone", new AddressTimeZoneFunction(faker));
            put("address_zip_code", new AddressZipCodeFunction(faker));
            put("address_zip_code_by_state", new AddressZipCodeByStateFunction(faker));
            put("ancient_god", new AncientGodFunction(faker));
            put("ancient_hero", new AncientHeroFunction(faker));
            put("ancient_primordial", new AncientPrimordialFunction(faker));
            put("ancient_titan", new AncientTitanFunction(faker));
            put("app_author", new AppAuthorFunction(faker));
            put("app_name", new AppNameFunction(faker));
            put("app_version", new AppVersionFunction(faker));
            put("artist_name", new ArtistNameFunction(faker));
            put("avatar_image", new AvatarImageFunction(faker));
            put("beer_hop", new BeerHopFunction(faker));
            put("beer_malt", new BeerMaltFunction(faker));
            put("beer_name", new BeerNameFunction(faker));
            put("beer_style", new BeerStyleFunction(faker));
            put("beer_yeast", new BeerYeastFunction(faker));
            put("book_author", new BookAuthorFunction(faker));
            put("book_genre", new BookGenreFunction(faker));
            put("book_publisher", new BookPublisherFunction(faker));
            put("book_title", new BookTitleFunction(faker));
            put("bool_bool", new BoolBoolFunction(faker));
            put("business_credit_card_expiry", new BusinessCreditCardExpiryFunction(faker));
            put("business_credit_card_number", new BusinessCreditCardNumberFunction(faker));
            put("business_credit_card_type", new BusinessCreditCardTypeFunction(faker));
            put("cat_breed", new CatBreedFunction(faker));
            put("cat_name", new CatNameFunction(faker));
            put("cat_registry", new CatRegistryFunction(faker));
            put("chucknorris_fact", new ChuckNorrisFactFunction(faker));
            put("code_asin", new CodeAsinFunction(faker));
            put("code_ean13", new CodeEan13Function(faker));
            put("code_ean8", new CodeEan8Function(faker));
            put("code_gtin13", new CodeGtin13Function(faker));
            put("code_gtin8", new CodeGtin8Function(faker));
            put("code_imei", new CodeImeiFunction(faker));
            put("code_isbn10", new CodeIsbn10Function(faker));
            put("code_isbn13", new CodeIsbn13Function(faker));
            put("code_isbn_group", new CodeIsbnGroupFunction(faker));
            put("code_isbn_gs1", new CodeIsbnGs1Function(faker));
            put("code_isbn_registrant", new CodeIsbnRegistrantFunction(faker));
            put("color_name", new ColorNameFunction(faker));
            put("commerce_color", new CommerceColorFunction(faker));
            put("commerce_department", new CommerceDepartmentFunction(faker));
            put("commerce_material", new CommerceMaterialFunction(faker));
            put("commerce_price", new CommercePriceFunction(faker));
            put("commerce_product_name", new CommerceProductNameFunction(faker));
            put("commerce_promotion_code", new CommercePromotionCodeFunction(faker));
            put("company_bs", new CompanyBsFunction(faker));
            put("company_buzzword", new CompanyBuzzwordFunction(faker));
            put("company_catch_phrase", new CompanyCatchPhraseFunction(faker));
            put("company_industry", new CompanyIndustryFunction(faker));
            put("company_logo", new CompanyLogoFunction(faker));
            put("company_name", new CompanyNameFunction(faker));
            put("company_profession", new CompanyProfessionFunction(faker));
            put("company_suffix", new CompanySuffixFunction(faker));
            put("company_url", new CompanyUrlFunction(faker));
            put("crypto_md5", new CryptoMd5Function(faker));
            put("crypto_sha1", new CryptoSha1Function(faker));
            put("crypto_sha256", new CryptoSha256Function(faker));
            put("crypto_sha512", new CryptoSha512Function(faker));
            put("currency_code", new CurrencyCodeFunction(faker));
            put("currency_name", new CurrencyNameFunction(faker));
            put("date_between", new DateBetweenFunction(faker));
            put("date_birthday", new DateBirthdayFunction(faker));
            put("date_future", new DateFutureFunction(faker));
            put("date_past", new DatePastFunction(faker));
            put("demographic_demonym", new DemographicDemonymFunction(faker));
            put("demographic_educational_attainment", new DemographicEducationalAttainmentFunction(faker));
            put("demographic_marital_status", new DemographicMaritalStatusFunction(faker));
            put("demographic_race", new DemographicRaceFunction(faker));
            put("demographic_sex", new DemographicSexFunction(faker));
            put("educator_campus", new EducatorCampusFunction(faker));
            put("educator_course", new EducatorCourseFunction(faker));
            put("educator_secondary_school", new EducatorSecondarySchoolFunction(faker));
            put("educator_university", new EducatorUniversityFunction(faker));
            put("esports_event", new EsportsEventFunction(faker));
            put("esports_game", new EsportsGameFunction(faker));
            put("esports_league", new EsportsLeagueFunction(faker));
            put("esports_player", new EsportsPlayerFunction(faker));
            put("esports_team", new EsportsTeamFunction(faker));
            put("file_extension", new FileExtensionFunction(faker));
            put("file_file_name", new FileFileNameFunction(faker));
            put("file_mime_type", new FileMimeTypeFunction(faker));
            put("finance_bic", new FinanceBicFunction(faker));
            put("finance_credit_card", new FinanceCreditCardFunction(faker));
            put("finance_iban", new FinanceIbanFunction(faker));
            put("food_ingredient", new FoodIngredientFunction(faker));
            put("food_measurement", new FoodMeasurementFunction(faker));
            put("food_spice", new FoodSpiceFunction(faker));
            put("friends_character", new FriendsCharacterFunction(faker));
            put("friends_location", new FriendsLocationFunction(faker));
            put("friends_quote", new FriendsQuoteFunction(faker));
            put("gameofthrones_character", new GameOfThronesCharacterFunction(faker));
            put("gameofthrones_city", new GameOfThronesCityFunction(faker));
            put("gameofthrones_dragon", new GameOfThronesDragonFunction(faker));
            put("gameofthrones_house", new GameOfThronesHouseFunction(faker));
            put("gameofthrones_quote", new GameOfThronesQuoteFunction(faker));
            put("hacker_abbreviation", new HackerAbbreviationFunction(faker));
            put("hacker_adjective", new HackerAdjectiveFunction(faker));
            put("hacker_ingverb", new HackerIngverbFunction(faker));
            put("hacker_noun", new HackerNounFunction(faker));
            put("hacker_verb", new HackerVerbFunction(faker));
            put("harrypotter_book", new HarryPotterBookFunction(faker));
            put("harrypotter_character", new HarryPotterCharacterFunction(faker));
            put("harrypotter_location", new HarryPotterLocationFunction(faker));
            put("harrypotter_quote", new HarryPotterQuoteFunction(faker));
            put("hipster_word", new HipsterWordFunction(faker));
            put("idnumber_invalid", new IdNumberInvalidFunction(faker));
            put("idnumber_invalid_sv_se_ssn", new IdNumberInvalidSvSeSsnFunction(faker));
            put("idnumber_ssn_valid", new IdNumberSsnValidFunction(faker));
            put("idnumber_valid", new IdNumberValidFunction(faker));
            put("idnumber_valid_sv_se_ssn", new IdNumberValidSvSeSsnFunction(faker));
            put("internet_avatar", new InternetAvatarFunction(faker));
            put("internet_domain_name", new InternetDomainNameFunction(faker));
            put("internet_domain_suffix", new InternetDomainSuffixFunction(faker));
            put("internet_domain_word", new InternetDomainWordFunction(faker));
            put("internet_email_address", new InternetEmailAddressFunction(faker));
            put("internet_image", new InternetImageFunction(faker));
            put("internet_ip_v4_address", new InternetIpV4AddressFunction(faker));
            put("internet_ip_v4_cidr", new InternetIpV4CidrFunction(faker));
            put("internet_ip_v6_address", new InternetIpV6AddressFunction(faker));
            put("internet_ip_v6_cidr", new InternetIpV6CidrFunction(faker));
            put("internet_mac_address", new InternetMacAddressFunction(faker));
            put("internet_password", new InternetPasswordFunction(faker));
            put("internet_private_ip_v4_address", new InternetPrivateIpV4AddressFunction(faker));
            put("internet_public_ip_v4_address", new InternetPublicIpV4AddressFunction(faker));
            put("internet_safe_email_address", new InternetSafeEmailAddressFunction(faker));
            put("internet_slug", new InternetSlugFunction(faker));
            put("internet_url", new InternetUrlFunction(faker));
            put("internet_uuid", new InternetUuidFunction(faker));
            put("job_field", new JobFieldFunction(faker));
            put("job_key_skills", new JobKeySkillsFunction(faker));
            put("job_position", new JobPositionFunction(faker));
            put("job_seniority", new JobSeniorityFunction(faker));
            put("job_title", new JobTitleFunction(faker));
            put("lebowski_actor", new LebowskiActorFunction(faker));
            put("lebowski_character", new LebowskiCharacterFunction(faker));
            put("lebowski_quote", new LebowskiQuoteFunction(faker));
            put("lordoftherings_character", new LordOfTheRingsCharacterFunction(faker));
            put("lordoftherings_location", new LordOfTheRingsLocationFunction(faker));
            put("lorem_character", new LoremCharacterFunction(faker));
            put("lorem_characters", new LoremCharactersFunction(faker));
            put("lorem_fixed_string", new LoremFixedStringFunction(faker));
            put("lorem_paragraph", new LoremParagraphFunction(faker));
            put("lorem_paragraphs", new LoremParagraphsFunction(faker));
            put("lorem_sentence", new LoremSentenceFunction(faker));
            put("lorem_sentences", new LoremSentencesFunction(faker));
            put("lorem_word", new LoremWordFunction(faker));
            put("lorem_words", new LoremWordsFunction(faker));
            put("matz_quote", new MatzQuoteFunction(faker));
            put("music_chord", new MusicChordFunction(faker));
            put("music_instrument", new MusicInstrumentFunction(faker));
            put("music_key", new MusicKeyFunction(faker));
            put("name_first_name", new NameFirstNameFunction(faker));
            put("name_full_name", new NameFullNameFunction(faker));
            put("name_last_name", new NameLastNameFunction(faker));
            put("name_name", new NameNameFunction(faker));
            put("name_name_with_middle", new NameNameWithMiddleFunction(faker));
            put("name_prefix", new NamePrefixFunction(faker));
            put("name_suffix", new NameSuffixFunction(faker));
            put("name_title", new NameTitleFunction(faker));
            put("name_username", new NameUsernameFunction(faker));
            put("number_digit", new NumberDigitFunction(faker));
            put("number_digits", new NumberDigitsFunction(faker));
            put("number_number_between", new NumberNumberBetweenFunction(faker));
            put("number_random_digit", new NumberRandomDigitFunction(faker));
            put("number_random_digit_not_zero", new NumberRandomDigitNotZeroFunction(faker));
            put("number_random_double", new NumberRandomDoubleFunction(faker));
            put("number_random_number", new NumberRandomNumberFunction(faker));
            put("phonenumber_cell_phone", new PhoneNumberCellPhoneFunction(faker));
            put("phonenumber_phone_number", new PhoneNumberPhoneNumberFunction(faker));
            put("pokemon_location", new PokemonLocationFunction(faker));
            put("pokemon_name", new PokemonNameFunction(faker));
            put("random_next_boolean", new RandomNextBooleanFunction(faker));
            put("random_next_double", new RandomNextDoubleFunction(faker));
            put("random_next_int", new RandomNextIntFunction(faker));
            put("random_next_long", new RandomNextLongFunction(faker));
            put("rickandmorty_character", new RickAndMortyCharacterFunction(faker));
            put("rickandmorty_location", new RickAndMortyLocationFunction(faker));
            put("rickandmorty_quote", new RickAndMortyQuoteFunction(faker));
            put("rockband_name", new RockBandNameFunction(faker));
            put("shakespeare_as_you_like_it_quote", new ShakespeareAsYouLikeItQuoteFunction(faker));
            put("shakespeare_hamlet_quote", new ShakespeareHamletQuoteFunction(faker));
            put("shakespeare_king_richard_i_i_i_quote", new ShakespeareKingRichardIIIQuoteFunction(faker));
            put("shakespeare_romeo_and_juliet_quote", new ShakespeareRomeoAndJulietQuoteFunction(faker));
            put("slackemoji_activity", new SlackEmojiActivityFunction(faker));
            put("slackemoji_celebration", new SlackEmojiCelebrationFunction(faker));
            put("slackemoji_custom", new SlackEmojiCustomFunction(faker));
            put("slackemoji_emoji", new SlackEmojiEmojiFunction(faker));
            put("slackemoji_food_and_drink", new SlackEmojiFoodAndDrinkFunction(faker));
            put("slackemoji_nature", new SlackEmojiNatureFunction(faker));
            put("slackemoji_objects_and_symbols", new SlackEmojiObjectsAndSymbolsFunction(faker));
            put("slackemoji_people", new SlackEmojiPeopleFunction(faker));
            put("slackemoji_travel_and_places", new SlackEmojiTravelAndPlacesFunction(faker));
            put("space_agency", new SpaceAgencyFunction(faker));
            put("space_agency_abbreviation", new SpaceAgencyAbbreviationFunction(faker));
            put("space_company", new SpaceCompanyFunction(faker));
            put("space_constellation", new SpaceConstellationFunction(faker));
            put("space_distance_measurement", new SpaceDistanceMeasurementFunction(faker));
            put("space_galaxy", new SpaceGalaxyFunction(faker));
            put("space_meteorite", new SpaceMeteoriteFunction(faker));
            put("space_moon", new SpaceMoonFunction(faker));
            put("space_nasa_space_craft", new SpaceNasaSpaceCraftFunction(faker));
            put("space_nebula", new SpaceNebulaFunction(faker));
            put("space_planet", new SpacePlanetFunction(faker));
            put("space_star", new SpaceStarFunction(faker));
            put("space_star_cluster", new SpaceStarClusterFunction(faker));
            put("stock_nsdq_symbol", new StockNsdqSymbolFunction(faker));
            put("stock_nyse_symbol", new StockNyseSymbolFunction(faker));
            put("superhero_descriptor", new SuperheroDescriptorFunction(faker));
            put("superhero_name", new SuperheroNameFunction(faker));
            put("superhero_power", new SuperheroPowerFunction(faker));
            put("superhero_prefix", new SuperheroPrefixFunction(faker));
            put("superhero_suffix", new SuperheroSuffixFunction(faker));
            put("team_creature", new TeamCreatureFunction(faker));
            put("team_name", new TeamNameFunction(faker));
            put("team_sport", new TeamSportFunction(faker));
            put("team_state", new TeamStateFunction(faker));
            put("twinpeaks_character", new TwinPeaksCharacterFunction(faker));
            put("twinpeaks_location", new TwinPeaksLocationFunction(faker));
            put("twinpeaks_quote", new TwinPeaksQuoteFunction(faker));
            put("university_name", new UniversityNameFunction(faker));
            put("university_prefix", new UniversityPrefixFunction(faker));
            put("university_suffix", new UniversitySuffixFunction(faker));
            put("weather_description", new WeatherDescriptionFunction(faker));
            put("weather_temperature_celsius", new WeatherTemperatureCelsiusFunction(faker));
            put("weather_temperature_fahrenheit", new WeatherTemperatureFahrenheitFunction(faker));
            put("witcher_character", new WitcherCharacterFunction(faker));
            put("witcher_location", new WitcherLocationFunction(faker));
            put("witcher_monster", new WitcherMonsterFunction(faker));
            put("witcher_quote", new WitcherQuoteFunction(faker));
            put("witcher_school", new WitcherSchoolFunction(faker));
            put("witcher_witcher", new WitcherWitcherFunction(faker));
            put("yoda_quote", new YodaQuoteFunction(faker));
            put("zelda_character", new ZeldaCharacterFunction(faker));
            put("zelda_game", new ZeldaGameFunction(faker));
        }};
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        return null;
    }

    @Override
    public List<BinaryOperator> getBinaryOperators() {
        return null;
    }

    @Override
    public List<UnaryOperator> getUnaryOperators() {
        return null;
    }

    @Override
    public Map<String, Object> getGlobalVariables() {
        return null;
    }

    @Override
    public List<NodeVisitorFactory> getNodeVisitors() {
        return null;
    }

    @Override
    public List<AttributeResolver> getAttributeResolver() {
        return null;
    }
}
