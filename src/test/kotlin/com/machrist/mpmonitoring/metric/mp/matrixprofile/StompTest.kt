package com.machrist.mpmonitoring.metric.mp.matrixprofile

import com.machrist.mpmonitoring.metric.mp.windowstatistic.RollingWindowStatistics
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Random

class StompTest {
    @Test
    fun test() {
        val windowSize = 30
        val initialStats = RollingWindowStatistics(windowSize, 200)

        val data = ArrayList<Double>()

        for (i in 0..399) {
            data.add(0.5 * Random().nextInt(100))
        }

        val initialData =
            data.stream()
                .mapToDouble { t -> t }
                .limit(200)
                .toArray()

        val stomp = Stomp(windowSize, 300)

        data.stream()
            .mapToDouble { t -> t!! }
            .limit(300)
            .forEach(stomp::update)

        val mp = stomp.get()

        for (initialDatum in initialData) {
            initialStats.apply(initialDatum)
        }

        val testData =
            data.stream()
                .mapToDouble { t: Double? -> t!! }
                .skip(200)
                .limit(100)
                .toArray()

        val stompi1 = Stompi(initialStats, 0)
        val stompi2 = Stompi(initialStats, 0)
        val stompi3 = Stompi(initialStats, 100)

        var incremental = stompi1.get()
        var withHistory = stompi3.get()

        for (testDatum in testData) {
            stompi1.update(testDatum)
            stompi2.update(testDatum)
            stompi3.update(testDatum)
            withHistory = stompi3.get()
            incremental = stompi1.get()
        }

        val incrementalBatch = stompi2.get()

        assertArrayEquals(incremental.indexes, mp!!.indexes)

        assertArrayEquals(incremental.leftIndexes, mp.leftIndexes)
        assertArrayEquals(incremental.rightIndexes, mp.rightIndexes)

        arrayEquals(incremental.profile, mp.profile)
        arrayEquals(incremental.leftProfile, mp.leftProfile)
        arrayEquals(incremental.rightProfile, mp.rightProfile)

        assertArrayEquals(incrementalBatch.indexes, mp.indexes)
        assertArrayEquals(incrementalBatch.leftIndexes, mp.leftIndexes)
        assertArrayEquals(incrementalBatch.rightIndexes, mp.rightIndexes)

        arrayEquals(incrementalBatch.profile, mp.profile)
        arrayEquals(incrementalBatch.leftProfile, mp.leftProfile)
        arrayEquals(incrementalBatch.rightProfile, mp.rightProfile)
    }

    @Test
    fun testStomp() {
        val limit = 200
        val windowSize = 30
        val check = loadCheck("matrixprofile/stomp_self_join.csv")

        val stomp = Stomp(windowSize, limit)

        data.stream()
            .mapToDouble { t -> t.x }
            .limit(limit.toLong())
            .forEach(stomp::update)

        val mp = stomp.get()

        arrayEquals(mp?.profile, check.profile)
        assertArrayEquals(mp?.indexes, check.indexes)
        arrayEquals(mp?.leftProfile, check.leftProfile)
        assertArrayEquals(mp?.leftIndexes, check.leftIndexes)
        arrayEquals(mp?.rightProfile, check.rightProfile)
        assertArrayEquals(mp?.rightIndexes, check.rightIndexes)
    }

    private fun arrayEquals(
        a: DoubleArray?,
        b: DoubleArray?,
    ) {
        assertNotNull(b)
        assertNotNull(a)
        assertEquals(a!!.size, b!!.size)
        for (i in a.indices) {
            assertEquals(a[i], b[i], 0.0001)
        }
    }

    private fun <T> loadCsvFromResource(
        fileName: String,
        transform: (List<List<String>>) -> T,
    ): T {
        println("Loading CSV from $fileName")
        println(this.javaClass.classLoader.getResource(fileName))

        val inputStream = this.javaClass.classLoader.getResourceAsStream(fileName)!!
        val reader = BufferedReader(InputStreamReader(inputStream))

        val result = arrayListOf<List<String>>()
        reader.use { r ->
            r.readLine()
            do {
                val line =
                    r.readLine()?.let {
                        val tokens = it.split(",")
                        result.add(tokens)
                    }
            } while (line != null)
        }
        return transform(result)
    }

    private val data =
        loadCsvFromResource("matrixprofile/mp_toy_data.csv") { rows ->
            rows.map { ToyData(it[0].toDouble(), it[1].toDouble(), it[2].toDouble()) }
        }

    data class ToyData(val x: Double, val y: Double, val z: Double)

    private fun loadCheck(
        fileName: String,
        partial: Boolean = false,
    ): BaseMatrixProfile {
        return loadCsvFromResource(fileName) { rows ->
            val rowsLength = rows.size
            val mp = DoubleArray(rowsLength)
            val lmp = DoubleArray(rowsLength)
            val rmp = DoubleArray(rowsLength)
            val pi = IntArray(rowsLength)
            val lpi = IntArray(rowsLength)
            val rpi = IntArray(rowsLength)

            rows.forEachIndexed { index, row ->
                mp[index] = parseDouble(row[0])
                pi[index] = parseInt(row[1]).let { if (it >= 0) it - 1 else -1 }

                if (!partial) {
                    lmp[index] = parseDouble(row[2])
                    lpi[index] = parseInt(row[3]).let { if (it >= 0) it - 1 else -1 }

                    rmp[index] = parseDouble(row[4])
                    rpi[index] = parseInt(row[5]).let { if (it >= 0) it - 1 else -1 }
                }
            }

            val baseMatrixProfile = BaseMatrixProfile(0, 0.0, mp, pi, rmp, lmp, rpi, lpi)
            baseMatrixProfile
        }
    }

    private fun parseDouble(value: String): Double {
        return when (value) {
            "Inf" -> Double.POSITIVE_INFINITY
            "-Inf" -> Double.NEGATIVE_INFINITY
            else -> value.toDouble()
        }
    }

    private fun parseInt(value: String): Int {
        if (value == "-Inf") {
            return -1
        }
        return value.toInt()
    }
}
