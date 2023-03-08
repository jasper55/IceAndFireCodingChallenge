package wagner.jasper.iceandfirecodingchallenge.housedetailspage.testdata

import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDTO
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDbEntity
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails

fun houseDetailsDBEntity(
    id: Int,
    url: String = "url",
    name: String = "name",
    region: String = "region",
    coatOfArms: String = "coatOfArms",
    words: String = "words",
    titles: List<String> = listOf(),
    seats: List<String> = listOf(),
    currentLordUrl: String = "currentLordUrl",
    heirUrl: String = "heirUrl",
    overlordUrl: String = "overlordUrl",
    founded: String = "founded",
    founderUrl: String = "founderUrl",
    diedOut: String = "diedOut",
    ancestralWeapons: List<String> = listOf(),
    cadetBranches: List<String> = listOf(),
    swornMembers: List<String> = listOf(),
) =
    HouseDetailsDbEntity(
        id = id,
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLordUrl,
        heir = heirUrl,
        overlord = overlordUrl,
        founded = founded,
        founder = founderUrl,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons,
        cadetBranches = cadetBranches,
        swornMembers = swornMembers,
    )

fun houseDetails(
    url: String = "url",
    name: String = "name",
    region: String = "region",
    coatOfArms: String = "coatOfArms",
    words: String = "words",
    titles: List<String> = listOf(),
    seats: List<String> = listOf(),
    currentLordUrl: String = "currentLordUrl",
    heirUrl: String = "heirUrl",
    overlordUrl: String = "overlordUrl",
    founded: String = "founded",
    founderUrl: String = "founderUrl",
    diedOut: String = "diedOut",
    ancestralWeapons: List<String> = listOf(),
    cadetBranches: List<String> = listOf(),
    swornMembers: List<String> = listOf(),
) =
    HouseDetails(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLordUrl,
        heir = heirUrl,
        overlord = overlordUrl,
        founded = founded,
        founder = founderUrl,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons,
        cadetBranches = cadetBranches,
        swornMembers = swornMembers,
    )

fun houseDetailsResponse(
    url: String = "url",
    name: String = "name",
    region: String = "region",
    coatOfArms: String = "coatOfArms",
    words: String = "words",
    titles: List<String> = listOf(),
    seats: List<String> = listOf(),
    currentLordUrl: String = "currentLordUrl",
    heirUrl: String = "heirUrl",
    overlordUrl: String = "overlordUrl",
    founded: String = "founded",
    founderUrl: String = "founderUrl",
    diedOut: String = "diedOut",
    ancestralWeapons: List<String> = listOf(),
    cadetBranches: List<String> = listOf(),
    swornMembers: List<String> = listOf(),
) =
    HouseDetailsDTO(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLordUrl,
        heir = heirUrl,
        overlord = overlordUrl,
        founded = founded,
        founder = founderUrl,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons,
        cadetBranches = cadetBranches,
        swornMembers = swornMembers,
    )
