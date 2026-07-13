package com.example.damas.data.repository

import com.example.damas.data.dao.PieceDao
import com.example.damas.data.entity.PieceEntity
import com.example.damas.data.mapper.PieceMapper.toDomain
import com.example.damas.data.mapper.PieceMapper.toEntity
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.enums.PieceColor
import com.example.damas.domain.repository.PieceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PieceRepositoryImpl @Inject constructor(
    private val pieceDao: PieceDao
) : PieceRepository {
    override fun getPiecesByColor(color: PieceColor): Flow<List<Piece>> =
        pieceDao.getPiecesByColor(color.name)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getPieceById(id: Int): Piece? =
        pieceDao.getPieceById(id)?.toDomain()

    override suspend fun getPieceByPosition(x: Int, y: Int): Piece? =
        pieceDao.getPieceByPosition(x, y)?.toDomain()

    override suspend fun movePiece(id: Int, x: Int, y: Int) {
        pieceDao.movePiece(id, x, y)
    }

    override suspend fun capturePiece(id: Int) {
        pieceDao.capturePiece(id)
    }

    override suspend fun promotePiece(id: Int) {
        pieceDao.promotePiece(id)
    }

    override suspend fun clearBoard() {
        pieceDao.clearBoard()
    }

    override suspend fun initializeBoard() =
        pieceDao.insertAll(board())
}

private fun board(): List<PieceEntity> = buildList {
    for (x in 0..7) {
        for (y in 0..7) {
            if ((x + y) % 2 == 1 && (y in 0..2 || y in 5..7)) {
                add(
                    Piece(
                        id = 0,
                        x = x,
                        y = y,
                        color = if (y < 4) PieceColor.BLACK else PieceColor.WHITE
                    ).toEntity()
                )
            }
        }
    }
}