package com.example.challengedraft
//
//package com.example.challengedraft
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [ParticipantEntity::class, ChallengeEntity::class], version = 5)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun participantDao(): ParticipantDao
//    abstract fun challengeDao(): ChallengeDao
//
//    companion object {
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "challenge_tracker_db"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
//
//@Dao
//interface ChallengeDao {
//    @Query("SELECT * FROM challenges")
//    fun getAllChallenges(): Flow<List<ChallengeEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(challenge: ChallengeEntity)
//
//    @Update
//    suspend fun update(challenge: ChallengeEntity)
//
//    @Delete
//    suspend fun delete(challenge: ChallengeEntity)
//}
//@Entity(tableName = "challenges")
//data class ChallengeEntity(
//    @PrimaryKey val id: String = UUID.randomUUID().toString(),
//    val title: String
//)
//
//fun ChallengeEntity.toChallengeItem(): ChallengeItem {
//    return ChallengeItem(
//        id = this.id,
//        title = this.title
//    )
//}
//
//fun ChallengeItem.toChallengeEntity(): ChallengeEntity {
//    return ChallengeEntity(id = id, title = title)
//}
//data class ChallengeItem(
//    val id: String = UUID.randomUUID().toString(),
//    val title: String = ""
//)
//
//@Composable
//fun ChallengeListScreen(
//    viewModel: ChallengeViewModel,
//    onChallengeClick: (String) -> Unit
//) {
//    val challenges by viewModel.challenges.collectAsState(initial = emptyList())
//
//    var newChallengeTitle by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        OutlinedTextField(
//            value = newChallengeTitle,
//            onValueChange = { newChallengeTitle = it },
//            label = { Text("New Challenge Title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(
//            onClick = {
//                if (newChallengeTitle.isNotBlank()) {
//                    viewModel.addChallenge(newChallengeTitle)
//                    newChallengeTitle = ""
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Add Challenge")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        LazyColumn {
//            items(challenges) { challenge ->
//                ChallengeItemRow(challenge) {
//                    onChallengeClick(challenge.id)
//                }
//            }
//        }
//
//    }
//}
//
//@Composable
//fun ChallengeItemRow(challenge: ChallengeItem, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable { onClick() }
//    ) {
//        Text(
//            text = challenge.title,
//            modifier = Modifier.padding(16.dp),
//            style = MaterialTheme.typography.body1
//        )
//    }
//}
//class ChallengeViewModelFactory(private val repository: ChallengeRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return ChallengeViewModel(repository) as T
//    }
//}
//
//class ChallengeRepository(private val dao: ChallengeDao) {
//    init {
//        startFirestoreListener()
//    }
//
//    private fun startFirestoreListener() {
//        Firebase.firestore.collection("challenges")
//            .addSnapshotListener { snapshots, error ->
//                if (error != null) return@addSnapshotListener
//                val list = snapshots?.toObjects(ChallengeItem::class.java) ?: return@addSnapshotListener
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    for (item in list) {
//                        dao.insert(item.toChallengeEntity())
//                    }
//                }
//            }
//    }
//
//    val allChallenges: Flow<List<ChallengeItem>> = dao.getAllChallenges().map { entities ->
//        entities.map { it.toChallengeItem() }
//    }
//
//    suspend fun addChallenge(challenge: ChallengeItem) {
//        dao.insert(challenge.toChallengeEntity())
//
//        Firebase.firestore.collection("challenges")
//            .document(challenge.id)
//            .set(challenge)
//            .await()
//    }
//
//    suspend fun updateChallenge(challenge: ChallengeItem) {
//        dao.update(challenge.toChallengeEntity())
//
//        Firebase.firestore.collection("challenges")
//            .document(challenge.id)
//            .set(challenge)
//            .await()
//    }
//}
//
//
//@Composable
//fun ChallengeTrackerApp(challengeId: String) {
//    val context = LocalContext.current
//    val db = AppDatabase.getDatabase(context)
//    val repository = ParticipantRepository(db.participantDao())
//
//    val viewModel: ParticipantViewModel = viewModel(
//        factory = ParticipantViewModelFactory(repository)
//    )
//
//    LaunchedEffect(challengeId) {
//        viewModel.setChallengeId(challengeId)
//    }
//
//    val participants by viewModel.participants.collectAsState()
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { viewModel.addParticipant(challengeId) }
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add Participant")
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            items(participants, key = { it.id!! }) { participant ->
//                ParticipantItem(
//                    participant = participant,
//                    onIncrement = { participant.id?.let { viewModel.increment(it) } },
//                    onDecrement = { participant.id?.let { viewModel.decrement(it) } },
//                    onNameChange = { newName -> participant.id?.let { viewModel.updateName(it, newName) } }
//                )
//            }
//        }
//    }
//}
//
//class ChallengeViewModel(private val repository: ChallengeRepository) : ViewModel() {
//    val challenges: Flow<List<ChallengeItem>> = repository.allChallenges
//
//    init {
//        syncFromFirestore()
//    }
//
//    fun addChallenge(title: String) {
//        viewModelScope.launch {
//            repository.addChallenge(ChallengeItem(title = title))
//        }
//    }
//
//    private fun syncFromFirestore() {
//        Firebase.firestore.collection("challenges")
//            .addSnapshotListener { snapshots, error ->
//                if (error != null) {
//                    // Handle error if needed
//                    return@addSnapshotListener
//                }
//
//                val list = snapshots?.toObjects(ChallengeItem::class.java) ?: return@addSnapshotListener
//
//                viewModelScope.launch(Dispatchers.IO) {
//                    for (item in list) {
//                        repository.updateChallenge(item)
//                    }
//                }
//            }
//    }
//}
//
//
//@Composable
//fun JoinChallengeScreen(
//    onJoinSuccess: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var code by remember { mutableStateOf("") }
//    var errorMessage by remember { mutableStateOf("") }
//    val context = LocalContext.current  // Get Android context here
//
//    Column(modifier.padding(16.dp)) {
//        TextField(
//            value = code,
//            onValueChange = { code = it },
//            label = { Text("Enter Challenge Code") },
//            modifier = Modifier.fillMaxWidth()  // <-- Added closing parenthesis here
//        )
//
//        Button(
//            onClick = {
//                joinChallengeByCode(
//                    context = context,
//                    code = code.trim(),
//                    onSuccess = onJoinSuccess,
//                    onFailure = { error ->
//                        errorMessage = error
//                    }
//                )
//            },
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            Text("Join Challenge")
//        }
//
//        if (errorMessage.isNotEmpty()) {
//            Text(
//                text = errorMessage,
//                color = Color.Red,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}
//
//// Pass context as parameter
//fun joinChallengeByCode(
//    context: android.content.Context,
//    code: String,
//    onSuccess: () -> Unit,
//    onFailure: (String) -> Unit
//) {
//    val firestore = FirebaseFirestore.getInstance()
//    firestore.collection("challenges")
//        .whereEqualTo("code", code)
//        .limit(1)
//        .get()
//        .addOnSuccessListener { result ->
//            if (result.isEmpty) {
//                onFailure("Challenge not found.")
//                return@addOnSuccessListener
//            }
//
//            val doc = result.documents.first()
//            val challenge = doc.toObject(ChallengeItem::class.java)
//
//            if (challenge == null) {
//                onFailure("Failed to parse challenge.")
//                return@addOnSuccessListener
//            }
//
//            // Save to Room DB on IO thread
//            CoroutineScope(Dispatchers.IO).launch {
//                AppDatabase.getDatabase(context).challengeDao().insert(challenge.toChallengeEntity())
//                withContext(Dispatchers.Main) {
//                    onSuccess()
//                }
//            }
//        }
//        .addOnFailureListener {
//            onFailure("Error: ${it.localizedMessage}")
//        }
//}
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
////                AppWithBottomNavigation()
//                MainScreen()
//            }
//        }
//    }
//}
//
//@Composable
//fun AppWithBottomNavigation() {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//
//    // Define Bottom Navigation items:
//    val items = listOf(
//        Screen.JoinChallenge,
//        Screen.Challenges
//        // You can add more screens here if needed
//    )
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.destination?.route
//
//                items.forEach { screen ->
//                    BottomNavigationItem(
//                        icon = { Icon(screen.icon, contentDescription = screen.title) },
//                        label = { Text(screen.title) },
//                        selected = currentRoute == screen.route,
//                        onClick = {
//                            if (currentRoute != screen.route) {
//                                navController.navigate(screen.route) {
//                                    // Pop up to the start destination to avoid building up back stack
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.JoinChallenge.route,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(Screen.JoinChallenge.route) {
//                JoinChallengeScreen(
//                    onJoinSuccess = {
//                        // Optionally navigate to challenges list on success:
//                        navController.navigate(Screen.Challenges.route) {
//                            popUpTo(Screen.JoinChallenge.route) { inclusive = true }
//                        }
//                    }
//                )
//            }
//            composable(Screen.Challenges.route) {
//                // Access DAO, repository, viewModel here
//                val dao = AppDatabase.getDatabase(context).challengeDao()
//                val repository = ChallengeRepository(dao)
//                val challengeViewModel: ChallengeViewModel = viewModel(
//                    factory = ChallengeViewModelFactory(repository)
//                )
//                ChallengeListScreen(
//                    viewModel = challengeViewModel,
//                    onChallengeClick = { challengeId ->
//                        navController.navigate("participants/$challengeId")
//                    }
//                )
//            }
//            composable(
//                "participants/{challengeId}",
//                arguments = listOf(navArgument("challengeId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val challengeId = backStackEntry.arguments?.getString("challengeId") ?: ""
//                ChallengeTrackerApp(challengeId = challengeId)
//            }
//        }
//    }
//}
//
//// Simple sealed class to define bottom nav screens with icons and titles
//sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
//    object JoinChallenge : Screen("joinChallenge", "Join", Icons.Default.Edit)
//    object Challenges : Screen("challenges", "Challenges", Icons.Default.List)
//}
//
//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//
//    // Get the DAO and ViewModel
//    val context = LocalContext.current
//    val dao = AppDatabase.getDatabase(context).challengeDao()
//    val repository = ChallengeRepository(dao)
//
//    val challengeViewModel: ChallengeViewModel = viewModel(
//        factory = ChallengeViewModelFactory(repository)
//    )
//
//    Scaffold(
//        bottomBar = { BottomNavBar(navController) }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.JoinChallenge.route,
//            modifier = Modifier.padding(innerPadding) // ✅ Correct use of Modifier
//        ) {
//            composable(Screen.JoinChallenge.route) {
//                JoinChallengeScreen(
//                    onJoinSuccess = {
//                        navController.navigate(Screen.Challenges.route)
//                    }
//                )
//            }
//            composable(Screen.Challenges.route) {
//                ChallengeListScreen(
//                    viewModel = challengeViewModel,
//                    onChallengeClick = { challengeId ->
//                        navController.navigate("participants/$challengeId")
//                    }
//                )
//            }
//        }
//    }
//}
//class MyApp : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        FirebaseApp.initializeApp(this)
//    }
//}
//data class Participant(
//    val id: String? = UUID.randomUUID().toString(),
//    val challengeId: String? = null,
//    val name: String? = null,
//    val count: Int? = null,
//    val hasEditedName: Boolean? = null
//) {
//    @Suppress("unused")
//    constructor() : this("-1", "", "", 0, false)
//
//    fun toEntity(): ParticipantEntity {
//        return ParticipantEntity(
//            id = UUID.fromString(id ?: UUID.randomUUID().toString()).toString(),
//            name = name ?: "",
//            count = count ?: 0,
//            hasEditedName = hasEditedName ?: false,
//            challengeId = challengeId ?: ""
//        )
//    }
//}
//@Dao
//interface ParticipantDao {
//    @Query("SELECT * FROM participants")
//    fun getAll(): Flow<List<ParticipantEntity>>
//
//    @Query("SELECT * FROM participants WHERE challengeId = :challengeId")
//    fun getByChallengeId(challengeId: String): Flow<List<ParticipantEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(participant: ParticipantEntity)
//
//    @Update
//    suspend fun update(participant: ParticipantEntity)
//
//    @Delete
//    suspend fun delete(participant: ParticipantEntity)
//
//    @Query("DELETE FROM participants")
//    suspend fun clearAll()
//}
//@Entity(tableName = "participants")
//data class ParticipantEntity(
//    @PrimaryKey val id: String,
//    val challengeId: String,
//    val name: String,
//    val count: Int,
//    val hasEditedName: Boolean
//)
//
//fun ParticipantEntity.toParticipant() = Participant(
//    id = this.id,
//    challengeId = this.challengeId,
//    name = this.name,
//    count = this.count,
//    hasEditedName = this.hasEditedName
//)
//
//@Composable
//fun ParticipantItem(
//    participant: Participant,
//    onIncrement: () -> Unit,
//    onDecrement: () -> Unit,
//    onNameChange: (String) -> Unit
//) {
//    var nameInput by remember(participant.id) { mutableStateOf(participant.name) }
//    val isEditable = !participant.hasEditedName!!
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            if (isEditable) {
//                nameInput?.let {
//                    OutlinedTextField(
//                        value = it,
//                        onValueChange = { nameInput = it },
//                        label = { Text("Name") },
//                        singleLine = true
//                    )
//                }
//                Spacer(modifier = Modifier.height(4.dp))
//                Button(onClick = {
//                    nameInput?.let { onNameChange(it) }
//                }) {
//                    Text("Save")
//                }
//            } else {
//                participant.name?.let { Text(text = it) }
//            }
//        }
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Button(onClick = onDecrement) { Text("-") }
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = participant.count.toString(), modifier = Modifier.width(40.dp))
//            Spacer(modifier = Modifier.width(8.dp))
//            Button(onClick = onIncrement) { Text("+") }
//        }
//    }
//}
//
//class ParticipantRepository(private val dao: ParticipantDao) {
//    init {
//        startFirestoreListener()
//    }
//
//    private fun startFirestoreListener() {
//        Firebase.firestore.collection("participants")
//            .addSnapshotListener { snapshots, error ->
//                if (error != null) return@addSnapshotListener
//                val list = snapshots?.toObjects(Participant::class.java) ?: return@addSnapshotListener
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    for (item in list) {
//                        dao.insert(item.toEntity())
//                    }
//                }
//            }
//    }
//    val allParticipants: Flow<List<Participant>> = dao.getAll().map { entities ->
//        entities.map { it.toParticipant() }
//    }
//
//    suspend fun add(participant: Participant) {
//        dao.insert(participant.toEntity())
//
//        Firebase.firestore.collection("participants")
//            .document(participant.id.toString())
//            .set(participant)
//            .await()
//    }
//
//    suspend fun update(participant: Participant) {
//        dao.update(participant.toEntity())
//
//        Firebase.firestore.collection("participants")
//            .document(participant.id.toString())
//            .set(participant)
//            .await()
//    }
//
//    fun getParticipantsForChallenge(challengeId: String): Flow<List<Participant>> {
//        return dao.getByChallengeId(challengeId).map { entities ->
//            entities.map { it.toParticipant() }
//        }
//    }
//}
//
//class ParticipantViewModel(
//    private val repository: ParticipantRepository
//) : ViewModel() {
//
//    private val _challengeId = MutableStateFlow<String?>(null)
//    private val _participants: StateFlow<List<Participant>> = _challengeId
//        .filterNotNull()
//        .flatMapLatest { repository.getParticipantsForChallenge(it) }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            emptyList()
//        )
//    val participants: StateFlow<List<Participant>> = _participants
//    fun setChallengeId(id: String) {
//        _challengeId.value = id
//    }
//
//
//    fun getParticipantsForChallenge(challengeId: String): Flow<List<Participant>> {
//        return repository.getParticipantsForChallenge(challengeId)
//    }
//
//    fun addParticipant(challengeId: String) {
//        val name = "Participant ${Random.nextInt(1000)}"
//        val newParticipant = Participant(name = name, count = 0, challengeId = challengeId)
//        viewModelScope.launch {
//            repository.add(newParticipant)
//        }
//    }
//
//    fun increment(id: String) {
//        updateCount(id) { it + 1 }
//    }
//
//    fun decrement(id: String) {
//        updateCount(id) { (it - 1).coerceAtLeast(0) }
//    }
//
//    fun updateName(id: String, newName: String) {
//        viewModelScope.launch {
//            val participant = participants.value.find { it.id == id } ?: return@launch
//            if (!participant.hasEditedName!!) {
//                val updated = participant.copy(name = newName, hasEditedName = true)
//                repository.update(updated)
//            }
//        }
//    }
//
//    private fun updateCount(id: String, operation: (Int) -> Int) {
//        viewModelScope.launch {
//            val participant = participants.value.find { it.id == id } ?: return@launch
//            val updated = participant.copy(count = participant.count?.let { operation(it) })
//            repository.update(updated)
//        }
//    }
//}
//class ParticipantViewModelFactory(private val repository: ParticipantRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ParticipantViewModel::class.java)) {
//            return ParticipantViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
//@Composable
//fun BottomNavBar(navController: NavHostController) {
//    val items = listOf(Screen.JoinChallenge, Screen.Challenges)
//    BottomNavigation {
//        val navBackStackEntry = navController.currentBackStackEntryAsState().value
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        items.forEach { screen ->
//            BottomNavigationItem(
//                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
//                label = { Text(screen.title) },
//                selected = currentRoute == screen.route,
//                onClick = {
//                    if (currentRoute != screen.route) {
//                        navController.navigate(screen.route) {
//                            popUpTo(navController.graph.startDestinationId) { saveState = true }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                }
//            )
//        }
//    }
//}