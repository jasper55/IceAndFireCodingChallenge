package wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDTO
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDbEntity
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails

fun HouseDetailsDbEntity.toDomain() =
    HouseDetails(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLord,
        heir = heir,
        overlord = overlord,
        founded = founded,
        founder = founder,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons,
        cadetBranches = cadetBranches,
        swornMembers = swornMembers,
    )

fun HouseDetailsDTO.toDomain() =
    HouseDetails(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles.filter { it.isNotBlank() },
        seats = seats.filter { it.isNotBlank() },
        currentLord = currentLord,
        heir = heir,
        overlord = overlord,
        founded = founded,
        founder = founder,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons.filter { it.isNotBlank() },
        cadetBranches = cadetBranches.filter { it.isNotBlank() },
        swornMembers = swornMembers.filter { it.isNotBlank() },
    )

fun HouseDetailsDTO.toDbEntity() =
    HouseDetailsDbEntity(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLord,
        heir = heir,
        overlord = overlord,
        founded = founded,
        founder = founder,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons,
        cadetBranches = cadetBranches,
        swornMembers = swornMembers,
    )
