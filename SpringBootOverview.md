@SpringBootApplication
│
├── Application Entry Point
│   ├── @SpringBootApplication
│   │   ├── @ComponentScan 
│   │   ├── @EnableAutoConfiguration
│   │   └── @Configuration
│   │
│   └── @ComponentScan 
│
├── Configuration Layer
│   ├── @Configuration
│   │   ├── @Bean 
│   │   ├── @ConfigurationProperties
│   │   ├── @PropertySource 
│   │   ├── @EnableConfigurationProperties
│   │   └── @EnableJpaRepositories 
│   │
│   └── @EnableAutoConfiguration 
│       └── Auto-configuration based on classpath
│
├── Component Layer
│   ├── @Component
│   ├── @Service 
│   │   └── @Transactional
│   ├── @Repository
│   │   ├── @Query 
│   │   ├── @Modifying 
│   │   ├── @Returning 
│   │   └── @NativeQuery
│   ├── @Entity 
│   │   ├── @Table 
│   │   ├── @Column 
│   │   ├── @PrimaryKey 
│   │   ├── @UniqueConstraint
│   │   ├── @OneToMany 
│   │   ├── @ManyToOne 
│   │   ├── @ManyToMany 
│   │   └── @GeneratedValue
│
├── Controller Layer
│   ├── @RestController 
│   ├── @Controller 
│   ├── @RequestMapping
│   ├── @GetMapping 
│   ├── @PostMapping
│   ├── @PutMapping 
│   ├── @PatchMapping 
│   ├── @DeleteMapping 
│   ├── @ResponseStatus
│   ├── @Valid 
│   └── @RequestParam 
│       ├── @PathVariable 
│       ├── @RequestHeader
│       ├── @CookieValue
│       └── @Header
│
├── Validation Layer
│   ├── @Valid 
│   ├── @Validated
│   ├── @NotNull 
│   ├── @NotNullOrEmpty
│   ├── @NotBlank
│   ├── @Size
│   ├── @Min 
│   ├── @Max 
│   ├── @Pattern 
│   ├── @Email 
│   ├── @Url 
│   ├── @Decimal
│   ├── @Future 
│   ├── @Past 
│   ├── @AssertTrue 
│   ├── @AssertFalse 
│   ├── @Positive 
│   └── @Negative 
│
├── Async & Scheduling 
│   ├── @Async 
│   ├── @Scheduled 
│   └── @AsyncConfiguration
│
├── Security Layer 
│   ├── @EnableGlobalMethodSecurity 
│   ├── @PreAuthorize 
│   ├── @PostAuthorize 
│   ├── @PreFilter 
│   ├── @PostFilter 
│   ├── @DenyAll 
│   └── @AllowAll 
│
└── Messaging Layer 
    ├── @SendTo
    ├── @Message 
    └── @MessageDriven
