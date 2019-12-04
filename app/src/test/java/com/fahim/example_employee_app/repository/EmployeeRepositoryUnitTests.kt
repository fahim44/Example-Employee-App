package com.fahim.example_employee_app.repository

import com.fahim.example_employee_app.TestEmployeeFactory
import com.fahim.example_employee_app.api.WebApiDataSource
import com.fahim.example_employee_app.db.LocalDBDataSource
import com.fahim.example_employee_app.preference.SharedPreference
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.spy
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("Unused")
@UseExperimental(ObsoleteCoroutinesApi::class,ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class EmployeeRepositoryUnitTests{
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val list = TestEmployeeFactory.makeEmployeeList()

    @Mock
    lateinit var localDBDataSource: LocalDBDataSource

    @Mock
    lateinit var webApiDataSource: WebApiDataSource

    @Mock
    lateinit var sharedPreference : SharedPreference


    lateinit var repository: EmployeeRepository

    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)

        repository = spy(EmployeeRepositoryImpl(localDBDataSource,webApiDataSource,sharedPreference))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun getInsertEmployees_whenDBDataSourceReturnNull_shouldReturnFalse(){
        Mockito.`when`(localDBDataSource.insertEmployees(list)).thenReturn(null)
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.insertEmployees(list)).isFalse()
            } }
    }

    @Test
    fun getInsertEmployees_whenDBDataSourceReturnEmptyList_shouldReturnFalse(){
        Mockito.`when`(localDBDataSource.insertEmployees(list)).thenReturn(listOf())
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.insertEmployees(list)).isFalse()
            } }
    }

    @Test
    fun getInsertEmployees_whenDBDataSourceReturnValidList_shouldReturnTrue(){
        Mockito.`when`(localDBDataSource.insertEmployees(list)).thenReturn(listOf(1))
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.insertEmployees(list)).isTrue()
            } }
    }


    @Test
    fun getUpdateEmployeeRating_whenIdIsInvalid_shouldReturnFalse(){
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.updateEmployeeRating(-1,2.5f)).isFalse()
            } }
    }

    @Test
    fun getUpdateEmployeeRating_whenRatingIsInvalid_shouldReturnFalse(){
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.updateEmployeeRating(1,-2.5f)).isFalse()
            } }
    }

    @Test
    fun getUpdateEmployeeRating_whenRatingIsGreaterThan5_shouldReturnFalse(){
        runBlocking {
            launch(Dispatchers.Main){
                Truth.assertThat(repository.updateEmployeeRating(1,5.0001f)).isFalse()
            } }
    }


    @Test
    fun getUpdateEmployeeRating_whenInputIsValid_shouldReturnTrue(){
        runBlocking {
            launch(Dispatchers.Main){
                Mockito.`when`(localDBDataSource.updateRating(1,2.5f)).thenReturn(true)
                Truth.assertThat(repository.updateEmployeeRating(1,2.5f)).isTrue()
            } }
    }


    @Test
    fun getDummyDataFromServerAndLoadToLocalDB_whenApiDataSourceReturnFalseAndNull_shouldReturnFalse(){
        runBlocking {
            launch(Dispatchers.Main){
                Mockito.`when`(webApiDataSource.retrieveDummyData()).thenReturn(Pair(false,null))
                Truth.assertThat(repository.getDummyDataFromServerAndLoadToLocalDB()).isFalse()
            } }
    }


    @Test
    fun getDummyDataFromServerAndLoadToLocalDB_whenInsertEmployeeReturnFalse_shouldReturnFalse(){
        runBlocking {
            launch(Dispatchers.Main){
                Mockito.`when`(webApiDataSource.retrieveDummyData()).thenReturn(Pair(true,list))
                Mockito.`when`(repository.insertEmployees(list)).thenReturn(false)
                Truth.assertThat(repository.getDummyDataFromServerAndLoadToLocalDB()).isFalse()
            } }
    }

    @Test
    fun getDummyDataFromServerAndLoadToLocalDB_whenRetrieveAndInsertSuccess_shouldReturnTrue(){
        runBlocking {
            launch(Dispatchers.Main){
                Mockito.`when`(webApiDataSource.retrieveDummyData()).thenReturn(Pair(true,list))
                Mockito.`when`(repository.insertEmployees(list)).thenReturn(true)
                Truth.assertThat(repository.getDummyDataFromServerAndLoadToLocalDB()).isTrue()
            } }
    }
}